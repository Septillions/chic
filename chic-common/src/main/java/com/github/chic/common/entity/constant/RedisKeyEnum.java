package com.github.chic.common.entity.constant;

/**
 * Redis Key 枚举类
 */
public enum RedisKeyEnum {
    /**
     * AUTH JWT 格式
     * auth:jwt:admin:{username}:{jwt}
     */
    AUTH_JWT_ADMIN_FORMAT("auth:jwt:admin:{}:{}"),
    /**
     * AUTH JWT 前缀
     */
    AUTH_JWT_ADMIN_PREFIX("auth:jwt:admin:"),
    /**
     * AUTH JWT 格式
     * auth:jwt:user:{mobile}:{jwt}
     */
    AUTH_JWT_USER_FORMAT("auth:jwt:user:{}:{}"),
    /**
     * AUTH JWT 前缀
     */
    AUTH_JWT_USER_PREFIX("auth:jwt:user:");

    private final String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
