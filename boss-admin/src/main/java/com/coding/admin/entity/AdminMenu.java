package com.coding.admin.entity;

import com.coding.common.base.BaseEntity;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AdminMenu extends BaseEntity implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 权限类型，页面-1，按钮-2
     */
    private Integer type;

    /**
     * 权限表达式
     */
    private String permission;


    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private Long parentId;
}
