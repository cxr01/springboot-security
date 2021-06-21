package com.coding.admin.serv.impl;

import com.coding.admin.entity.AdminUser;
import com.coding.admin.repo.AdminUserRepo;
import com.coding.admin.serv.AdminUserServ;
import com.coding.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cxr
 * @date 2021/2/23 17:03
 */
@Service
public class AdminUserServImpl implements AdminUserServ {

    @Autowired
    private AdminUserRepo adminUserRepo;

    @Override
    public CommonResult findAll() {
        return CommonResult.successResult("qqqq");
    }

    @Override
    public void create(AdminUser adminUser) {
        adminUser.setUsername("张三");
        adminUserRepo.save(adminUser);
    }
}
