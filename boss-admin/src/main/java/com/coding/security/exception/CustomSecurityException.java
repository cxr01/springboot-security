package com.coding.security.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限异常
 *  这里单独定义的SpringSecurity异常，在JwtAuthenticationFilter中进行处理
 *  理解：crud接口异常在 GlobalExceptionHandler 全局处理，security异常通过try-catch捕获后返回
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomSecurityException extends SecurityException {
    private long code;


    public CustomSecurityException(String message){
        super(message);
    }


}
