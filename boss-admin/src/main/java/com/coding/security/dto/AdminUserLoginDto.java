package com.coding.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求参数
 */
@Data
public class AdminUserLoginDto {

    /**
     * 用户名或邮箱或手机号
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
