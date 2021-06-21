package com.coding.admin.repo;

import com.coding.admin.entity.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cxr
 * @date 2021/2/23 16:43
 */
@Repository
public interface AdminMenuRepo extends JpaRepository<AdminMenu, Long>, JpaSpecificationExecutor<AdminMenu> {

    /**
     * 根据角色列表查询菜单列表
     */
    @Query(value = "select distinct t2.* from admin_role_menu t1,admin_menu t2 where t1.menu_id = t2.id and t1.role_id IN (:ids)",nativeQuery = true)
    List<AdminMenu> selectByRoleIdList(@Param("ids") List<Long> ids);

}
