package com.coding.admin.api;

import com.coding.admin.entity.AdminUser;
import com.coding.admin.serv.AdminUserServ;
import com.coding.common.api.CommonResult;
import com.coding.common.constant.ServerNameConstant;
import com.coding.security.annotation.rest.AnonymousGetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author cxr
 * @date 2021/2/23 13:25
 */
@RestController
@RequestMapping(ServerNameConstant.ADMIN + "/demoApi")
public class DemoApi {

    @Autowired
    private AdminUserServ adminUserServ;

    @GetMapping("/findAll")
    @PreAuthorize("@el.check('menu:find')")
    public CommonResult findAll() {
        return adminUserServ.findAll();
    }


    @PostMapping("/save")
    @PreAuthorize("@el.check('role1','menu:save')")
    public CommonResult create() {
        adminUserServ.create(new AdminUser());
        return CommonResult.successResult();
    }

    /**
     * 没有使用@PreAuthorize
     * 如果没有携带token请求会报错，携带token可以正常访问
     * 相当于，登录后才能访问，只是不需要具体权限了
     */
    @GetMapping("/findTest")
    public CommonResult findTest() {
        return adminUserServ.findAll();
    }


    /**
     * 测试匿名访问，即登录、不登录都可以访问
     */
    @AnonymousGetMapping("/testAnonymousAccess")
    public CommonResult testAnonymousAccess() {
        return adminUserServ.findAll();
    }

}
