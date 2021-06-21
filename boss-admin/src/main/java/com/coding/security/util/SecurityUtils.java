package com.coding.security.util;


import com.coding.security.user.AdminUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@UtilityClass
public class SecurityUtils {

    /**
     * 获取当前用户信息
     */
    public AdminUserDetails getAdminUser() {
        Authentication authentication = getAuthentication();
        return getAdminUser(authentication);
    }


    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public AdminUserDetails getAdminUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof AdminUserDetails) {
            return (AdminUserDetails) principal;
        }
        return null;
    }




}
