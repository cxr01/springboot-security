package com.coding.common.exception;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义API异常
 */
@Data
@NoArgsConstructor
public class ApiException extends RuntimeException {

    String msg;

    public ApiException(String message) {
        super(message);
    }


}
