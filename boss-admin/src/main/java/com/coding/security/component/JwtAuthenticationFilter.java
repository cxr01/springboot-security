package com.coding.security.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.coding.common.api.ResultCode;
import com.coding.security.annotation.AnonymousAccess;
import com.coding.security.config.CustomConfig;
import com.coding.security.user.service.admin.AdminUserDetailsService;
import com.coding.security.util.JwtUtil;
import com.coding.security.util.ResponseUtil;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * Jwt 权限认证过滤器，拦截所有请求，并判断是否有权限
 * </p>
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomConfig customConfig;

    @Autowired
    private WebApplicationContext applicationContext;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (checkIgnoresByProfile(request) || checkIgnoresByAnnotation(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtUtil.getJwtFromRequest(request);

        if (StrUtil.isNotBlank(jwt)) {
            try {
                String username = jwtUtil.getUsernameFromJWT(jwt);

                UserDetails userDetails = adminUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (SecurityException e) {
                ResponseUtil.renderJson(response, e);
            }
        } else {
            ResponseUtil.renderJson(response, ResultCode.UNAUTHORIZED, null);
        }

    }

    /**
     * 请求是否需要进行权限拦截(从配置文件 application.yml 中获取路径)
     *
     * @param request 当前请求
     * @return true - 放行，false - 拦截
     */
    private boolean checkIgnoresByProfile(HttpServletRequest request) {
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = Sets.newHashSet();

        switch (httpMethod) {
            case GET:
                ignores.addAll(customConfig.getIgnores().getGet());
                break;
            case PUT:
                ignores.addAll(customConfig.getIgnores().getPut());
                break;
            case HEAD:
                ignores.addAll(customConfig.getIgnores().getHead());
                break;
            case POST:
                ignores.addAll(customConfig.getIgnores().getPost());
                break;
            case PATCH:
                ignores.addAll(customConfig.getIgnores().getPatch());
                break;
            case TRACE:
                ignores.addAll(customConfig.getIgnores().getTrace());
                break;
            case DELETE:
                ignores.addAll(customConfig.getIgnores().getDelete());
                break;
            case OPTIONS:
                ignores.addAll(customConfig.getIgnores().getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(customConfig.getIgnores().getPattern());

        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 请求是否需要进行权限拦截(从注解 @AnonymousAccess 中获取路径)
     *
     * @param request 当前请求
     * @return true - 放行，false - 拦截
     */
    private boolean checkIgnoresByAnnotation(HttpServletRequest request) {
        Map<String, RequestMappingHandlerMapping> mappings = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = new HashMap<>(2);
        mappings.forEach((k, v) -> {
            map.putAll(v.getHandlerMethods());
        });

        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : map.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            //获取方法上的匿名类
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
                String method = requestMethods.get(0).name();
                Set<String> url = infoEntry.getKey().getPatternsCondition().getPatterns();
                List<String> urlList = new ArrayList(url);

                AntPathRequestMatcher matcher = new AntPathRequestMatcher(urlList.get(0), method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }
}