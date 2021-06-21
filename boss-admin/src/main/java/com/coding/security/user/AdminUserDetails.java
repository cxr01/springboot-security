package com.coding.security.user;

import com.coding.admin.entity.AdminMenu;
import com.coding.admin.entity.AdminRole;
import com.coding.admin.entity.AdminUser;
import com.coding.security.constant.SecurityConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cxr
 * @date 2021/2/24 14:07
 */
@Data
@AllArgsConstructor
public class AdminUserDetails implements UserDetails {


    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Boolean disabled;

    private List<String> roleNames;

    private Collection<? extends GrantedAuthority> authorities;

    public static AdminUserDetails create(AdminUser user, List<AdminRole> roles, List<AdminMenu> permissions) {
        List<String> roleNames = roles.stream().map(AdminRole::getName).collect(Collectors.toList());

        List<String> authorities = permissions.stream()
                .filter(permission -> !StringUtils.isEmpty(permission.getPermission()))
                .map(permission -> permission.getPermission()).collect(Collectors.toList());

        Set<String> AuthsSet = new HashSet<>();
        roleNames.forEach(v -> AuthsSet.add(v));
        AuthsSet.addAll(authorities);

        Collection<? extends GrantedAuthority> authoritie = AuthorityUtils.createAuthorityList(AuthsSet.toArray(new String[AuthsSet.size()]));

        return new AdminUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getPhone(), user.getEmail(), user.getDisabled(), roleNames, authoritie);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
