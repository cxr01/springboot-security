package com.coding.admin.serv;

import com.coding.common.api.CommonResult;
import com.coding.admin.entity.AdminUser;

/**
 * @author cxr
 * @date 2021/2/23 17:02
 */
public interface AdminUserServ {

    CommonResult findAll();

    void create(AdminUser adminUser);
}
