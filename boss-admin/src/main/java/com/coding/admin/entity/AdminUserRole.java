package com.coding.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author cxr
 * @date 2021/2/24 12:53
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AdminUserRole implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;
}
