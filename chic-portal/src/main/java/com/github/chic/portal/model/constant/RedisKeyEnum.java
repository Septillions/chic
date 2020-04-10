package com.github.chic.portal.model.constant;

/**
 * Redis Key 枚举类
 */
public enum RedisKeyEnum {
    /**
     * AUTH JWT 格式
     * auth:jwt:user:{mobile}:{jwt}
     */
    AUTH_JWT_FORMAT("auth:jwt:user:{}:{}"),
    /**
     * AUTH JWT 前缀
     */
    AUTH_JWT_PREFIX("auth:jwt:user:");
    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
