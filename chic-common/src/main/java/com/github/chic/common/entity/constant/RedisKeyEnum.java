package com.github.chic.common.entity.constant;

/**
 * Redis Key 枚举类
 */
public enum RedisKeyEnum {
    /**
     * AUTH JWT ACCESS 格式
     * auth:admin:jwt:access:{username}:{jwt}
     */
    AUTH_ADMIN_JWT_ACCESS_FORMAT("auth:admin:jwt:access:{}:{}"),
    /**
     * AUTH JWT ACCESS 前缀
     */
    AUTH_ADMIN_JWT_ACCESS_PREFIX("auth:admin:jwt:access:"),
    /**
     * AUTH JWT REFRESH 格式
     * auth:admin:jwt:refresh:{mobile}:{jwt}
     */
    AUTH_ADMIN_JWT_REFRESH_FORMAT("auth:admin:jwt:refresh:{}:{}"),
    /**
     * AUTH JWT REFRESH 前缀
     */
    AUTH_ADMIN_JWT_REFRESH_PREFIX("auth:admin:jwt:refresh:"),
    /**
     * AUTH JWT ACCESS 格式
     * auth:user:jwt:access{mobile}:{jwt}
     */
    AUTH_USER_JWT_ACCESS_FORMAT("auth:user:jwt:access:{}:{}"),
    /**
     * AUTH JWT ACCESS 前缀
     */
    AUTH_USER_JWT_ACCESS_PREFIX("auth:user:jwt:access:"),
    /**
     * AUTH JWT REFRESH 格式
     * auth:user:jwt:refresh:{mobile}:{jwt}
     */
    AUTH_USER_JWT_REFRESH_FORMAT("auth:user:jwt:refresh:{}:{}"),
    /**
     * AUTH JWT REFRESH 前缀
     */
    AUTH_USER_JWT_REFRESH_PREFIX("auth:user:jwt:refresh:");

    private final String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
