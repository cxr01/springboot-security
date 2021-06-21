package com.coding.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.coding.common.config.redis.RedisService;
import com.coding.common.exception.ApiException;
import com.coding.security.config.JwtConfig;
import com.coding.security.constant.SecurityConstant;
import com.coding.security.exception.CustomSecurityException;
import com.coding.security.user.AdminUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * JWT 工具类
 * </p>
 *
 */
@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Slf4j
public class JwtUtil {
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisService redisService;


    /**
     * 创建 JWT
     * @param authentication
     * @return
     */
    public String createJWT(Authentication authentication) {
        AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
        return createJWT(userDetails.getId(), userDetails.getUsername(), userDetails.getRoleNames(), userDetails.getAuthorities());
    }


    /**
     * 创建JWT
     *
     * @param id          用户id
     * @param subject     用户名
     * @param roles       用户角色
     * @param authorities 用户权限
     * @return JWT
     */
    public String createJWT(Long id, String subject, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(now).signWith(SignatureAlgorithm.HS256, jwtConfig.getKey())
                .claim("roles", roles).claim("authorities", authorities);

        // 设置过期时间
        Long ttl = jwtConfig.getRemember();     //七天
        //Long ttl = jwtConfig.getTtl();      //十分钟
        if (ttl > 0) {
            builder.setExpiration(DateUtil.offsetSecond(now, ttl.intValue()));
        }
        String jwt = builder.compact();

        // 将生成的JWT保存至Redis
        redisService.set(SecurityConstant.REDIS_JWT_KEY_PREFIX + subject, jwt, ttl);
        return jwt;
    }


    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }


    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT
        redisService.del(SecurityConstant.REDIS_JWT_KEY_PREFIX + username);
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtConfig.getKey()).parseClaimsJws(jwt).getBody();

            String username = claims.getSubject();
            String redisKey = SecurityConstant.REDIS_JWT_KEY_PREFIX + username;

            // 校验redis中的JWT是否存在
            Long expire = redisService.getExpire(redisKey);
            if (Objects.isNull(expire) || expire <= 0) {
                throw new ApiException("token 已过期，请重新登录!");
            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = (String) redisService.get(redisKey);
            if (!StrUtil.equals(jwt, redisToken)) {
                throw new CustomSecurityException("当前用户已在别处登录，请尝试更改密码或重新登录!");
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new CustomSecurityException("token 已过期，请重新登录！");
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new CustomSecurityException("token 解析失败，请尝试重新登录");
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new CustomSecurityException("token 解析失败，请尝试重新登录");
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new CustomSecurityException("token 解析失败，请尝试重新登录");
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new CustomSecurityException("token 解析失败，请尝试重新登录");
        }
    }





    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
