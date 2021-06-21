package com.coding.admin.repo;

import com.coding.admin.entity.AdminRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author cxr
 * @date 2021/2/23 16:43
 */
@Repository
public interface AdminRoleMenuRepo extends JpaRepository<AdminRoleMenu, Long>, JpaSpecificationExecutor<AdminRoleMenu> {
}
