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
public class AdminRole extends BaseEntity implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名
     */
    @Column(unique = true)
    private String name;

    /**
     * 描述
     */
    private String description;
}
