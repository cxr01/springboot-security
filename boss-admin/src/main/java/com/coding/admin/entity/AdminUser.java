package com.coding.admin.entity;

import com.coding.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author cxr
 * @date 2021/2/23 14:39
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AdminUser extends BaseEntity implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(unique = true)
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态，禁用-1，启用-0
     */
    private Boolean disabled;
}
