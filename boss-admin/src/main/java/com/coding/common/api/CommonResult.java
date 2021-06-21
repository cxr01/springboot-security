package com.coding.common.api;

import com.coding.common.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {

    /**
     * 状态码
     */
    private long code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据封装
     */
    private T data;




    /**
     * 成功
     */
    public static <T> CommonResult<T> successResult() {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功
     * @param data 返回结果
     */
    public static <T> CommonResult<T> successResult(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }


    /**
     * 失败
     */
    public static <T> CommonResult<T> failedResult() {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    /**
     * 失败
     */
    public static <T> CommonResult<T> failedResult(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailedResult(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 失败
     * @param resultCode    指定结果
     */
    public static <T> CommonResult<T> failedResult(ResultCode resultCode) {
        return new CommonResult<T>(resultCode.getCode(), resultCode.getMessage(), null);
    }


    /**
     * 构造一个异常的API返回
     *
     * @param t   异常
     * @param <T> {@link ApiException} 的子类
     * @return ApiResponse
     */
    public static <T> CommonResult<T> exceptionResult(RuntimeException t) {
        return failedResult(t.getMessage());
    }
}
