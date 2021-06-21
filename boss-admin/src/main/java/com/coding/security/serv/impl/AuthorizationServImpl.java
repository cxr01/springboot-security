package com.coding.security.serv.impl;

import com.coding.common.exception.Asserts;
import com.coding.security.serv.AuthorizationServ;
import com.coding.security.user.AdminUserDetails;
import com.coding.security.user.service.admin.AdminUserDetailsService;
import com.coding.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cxr
 * @date 2021/2/24 17:32
 */
@Slf4j
@Service
public class AuthorizationServImpl implements AuthorizationServ {

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(String username, String password) {
        String token =null;
        try {
            AdminUserDetails userDetails = (AdminUserDetails)adminUserDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            token = jwtUtil.createJWT(authentication);
        }catch (Exception e){
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public void logout(HttpServletRequest request) throws RuntimeException{
        // 设置JWT过期
        jwtUtil.invalidateJWT(request);
    }
}
