package com.github.chic.common.component.constant;

/**
 * Redis Key Auth 认证枚举类
 */
public enum RedisKeyAuthEnum {
    /**
     * ADMIN AUTH JWT ACCESS 格式
     * admin:auth:jwt:access:{username}:{jwt}
     */
    ADMIN_AUTH_JWT_ACCESS_FORMAT("admin:auth:jwt:access:{}:{}"),
    /**
     * ADMIN AUTH JWT ACCESS 前缀
     */
    ADMIN_AUTH_JWT_ACCESS_PREFIX("admin:auth:jwt:access:"),
    /**
     * ADMIN AUTH JWT REFRESH 格式
     * admin:auth:jwt:refresh:{username}:{jwt}
     */
    ADMIN_AUTH_JWT_REFRESH_FORMAT("admin:auth:jwt:refresh:{}:{}"),
    /**
     * ADMIN AUTH JWT REFRESH 前缀
     */
    ADMIN_AUTH_JWT_REFRESH_PREFIX("admin:auth:jwt:refresh:"),
    /**
     * ADMIN AUTH JWT BUFFER 格式
     * admin:auth:jwt:buffer:{username}:{jwt}
     */
    ADMIN_AUTH_JWT_BUFFER_FORMAT("admin:auth:jwt:buffer:{}:{}"),
    /**
     * ADMIN AUTH JWT BUFFER 前缀
     */
    ADMIN_AUTH_JWT_BUFFER_PREFIX("admin:auth:jwt:buffer:"),
    /**
     * USER AUTH JWT ACCESS 格式
     * user:auth:jwt:access{mobile}:{jwt}
     */
    USER_AUTH_JWT_ACCESS_FORMAT("user:auth:jwt:access:{}:{}"),
    /**
     * USER AUTH JWT ACCESS 前缀
     */
    USER_AUTH_JWT_ACCESS_PREFIX("user:auth:jwt:access:"),
    /**
     * USER AUTH JWT REFRESH 格式
     * user:auth:jwt:refresh:{mobile}:{jwt}
     */
    USER_AUTH_JWT_REFRESH_FORMAT("user:auth:jwt:refresh:{}:{}"),
    /**
     * USER AUTH JWT REFRESH 前缀
     */
    USER_AUTH_JWT_REFRESH_PREFIX("user:auth:jwt:refresh:"),
    /**
     * USER AUTH JWT BUFFER 格式
     * user:auth:jwt:buffer:{mobile}:{jwt}
     */
    USER_AUTH_JWT_BUFFER_FORMAT("user:auth:jwt:buffer:{}:{}"),
    /**
     * USER AUTH JWT BUFFER 前缀
     */
    USER_AUTH_JWT_BUFFER_PREFIX("user:auth:jwt:buffer:");

    private final String key;

    RedisKeyAuthEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
