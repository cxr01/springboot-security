package com.coding.admin.repo;

import com.coding.admin.entity.AdminRole;
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
public interface AdminRoleRepo extends JpaRepository<AdminRole, Long>, JpaSpecificationExecutor<AdminRole> {
    /**
     * 根据用户id查角色
     */
    @Query(value = "select t2.* from admin_user_role t1,admin_role t2 where t1.role_id = t2.id and t1.user_id = :userId", nativeQuery = true)
    List<AdminRole> selectByUserId(@Param("userId") Long userId);
}
