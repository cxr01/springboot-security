package com.coding.security.constant;

/**
 * @author cxr
 * @date 2021/2/24 17:03
 */
public interface SecurityConstant {


    String AUTH_URL = "/auth";

    String ROLE = "ROLE_";

    /**
     * JWT 在 Redis 中保存的key前缀
     */
    String REDIS_JWT_KEY_PREFIX = "security:jwt:";
}
