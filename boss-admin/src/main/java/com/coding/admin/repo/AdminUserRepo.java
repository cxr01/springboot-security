package com.coding.admin.repo;

import com.coding.admin.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author cxr
 * @date 2021/2/23 16:43
 */
@Repository
public interface AdminUserRepo extends JpaRepository<AdminUser, Long>, JpaSpecificationExecutor<AdminUser> {

    /**
     * 根据用户名、邮箱、手机号查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    Optional<AdminUser> findByUsername(String username);
}
