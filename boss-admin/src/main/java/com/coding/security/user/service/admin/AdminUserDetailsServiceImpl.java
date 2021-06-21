package com.coding.security.user.service.admin;

import com.coding.common.api.CommonResult;
import com.coding.common.api.ResultCode;
import com.coding.security.user.AdminUserDetails;
import com.coding.admin.entity.AdminMenu;
import com.coding.admin.entity.AdminRole;
import com.coding.admin.entity.AdminUser;
import com.coding.admin.repo.AdminMenuRepo;
import com.coding.admin.repo.AdminRoleRepo;
import com.coding.admin.repo.AdminUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cxr
 * @date 2021/2/24 14:04
 */
@Service
public class AdminUserDetailsServiceImpl implements AdminUserDetailsService {

    @Autowired
    private AdminUserRepo adminUserRepo;
    @Autowired
    private AdminRoleRepo adminRoleRepo;
    @Autowired
    private AdminMenuRepo adminMenuRepo;


    /**
     * 根据用户名查用户信息、角色、权限
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(username)){
            CommonResult.failedResult(ResultCode.VALIDATE_FAILED);
        }
//        AdminUser adminUser = adminUserRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("未找到用户信息 : " + username));
//        List<AdminRole> adminRoles = adminRoleRepo.selectByUserId(adminUser.getId());
//        List<Long> adminRolesId = adminRoles.stream().map(AdminRole::getId).collect(Collectors.toList());
//        List<AdminMenu> adminMenus = adminMenuRepo.selectByRoleIdList(adminRolesId);
//        return AdminUserDetails.create(adminUser, adminRoles, adminMenus);

        // TODO: 2021/2/25 测试时，将密码加密，做好新增用户后，改回来，在新增用户时加密即可
        AdminUser adminUser = new AdminUser().setUsername("zs").setPassword(new BCryptPasswordEncoder().encode("123")).setDisabled(false).setEmail("m13663485663@163.com").setId(1L).setPhone("13663485663");
        List<AdminRole> adminRoles = new ArrayList<>();
        adminRoles.add(new AdminRole(1L,"role1","角色测试描述"));
        List<AdminMenu> adminMenus = new ArrayList<>();
        //adminMenus.add(new AdminMenu(1L,"菜单1","path",1,"menu:find",1,null));
        return AdminUserDetails.create(adminUser, adminRoles, adminMenus);
    }
}
