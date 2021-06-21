package com.coding.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author cxr
 * @date 2021/2/24 13:28
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AdminRoleMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 菜单id
     */
    @Column(name = "menu_id")
    private Long menuId;
}
