package com.coding.security.user.service.admin;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 实现UserDetailsService，可以重构loadUserByUsername方法，该方法内可以实现数据库中根据用户名(账号)查询用户信息（角色，权限）
 */
public interface AdminUserDetailsService extends UserDetailsService {
}
