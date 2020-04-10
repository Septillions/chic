package com.github.chic.admin.model.constant;

/**
 * Redis Key 枚举类
 */
public enum RedisKeyEnum {
    /**
     * AUTH JWT 格式
     * auth:jwt:admin:{username}:{jwt}
     */
    AUTH_JWT_FORMAT("auth:jwt:admin:{}:{}"),
    /**
     * AUTH JWT 前缀
     */
    AUTH_JWT_PREFIX("auth:jwt:admin:");
    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
