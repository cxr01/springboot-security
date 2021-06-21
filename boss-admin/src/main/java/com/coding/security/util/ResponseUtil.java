package com.coding.security.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coding.common.api.CommonResult;
import com.coding.common.api.ResultCode;
import com.coding.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * Response 通用工具类
 * </p>
 *
 */
@Slf4j
public class ResponseUtil {

    /**
     * 往 response 写出 json
     *
     * @param response 响应
     * @param status   状态
     * @param data     返回数据
     */
    public static void renderJson(HttpServletResponse response, ResultCode status, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);

            // FIXME: hutool 的 BUG：JSONUtil.toJsonStr()
            //  将JSON转为String的时候，忽略null值的时候转成的String存在错误
            response.getWriter().write(JSONUtil.toJsonStr(new JSONObject(CommonResult.failedResult(status), false)));
        } catch (IOException e) {
            log.error("Response写出JSON异常，", e);
        }
    }

    /**
     * 往 response 写出 json
     *
     * @param response  响应
     * @param exception 异常
     */
    public static void renderJson(HttpServletResponse response, RuntimeException exception) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);

            // FIXME: hutool 的 BUG：JSONUtil.toJsonStr()
            //  将JSON转为String的时候，忽略null值的时候转成的String存在错误
            response.getWriter().write(JSONUtil.toJsonStr(new JSONObject(CommonResult.exceptionResult(exception), false)));
        } catch (IOException e) {
            log.error("Response写出JSON异常，", e);
        }
    }
}
