package com.github.chic.common.component.constant;

/**
 * Redis Key 枚举类
 */
public enum RedisKeyEnum {
    /**
     * AUTH ADMIN JWT ACCESS 格式
     * auth:admin:jwt:access:{username}:{jwt}
     */
    AUTH_ADMIN_JWT_ACCESS_FORMAT("auth:admin:jwt:access:{}:{}"),
    /**
     * AUTH ADMIN JWT ACCESS 前缀
     */
    AUTH_ADMIN_JWT_ACCESS_PREFIX("auth:admin:jwt:access:"),
    /**
     * AUTH ADMIN JWT REFRESH 格式
     * auth:admin:jwt:refresh:{username}:{jwt}
     */
    AUTH_ADMIN_JWT_REFRESH_FORMAT("auth:admin:jwt:refresh:{}:{}"),
    /**
     * AUTH ADMIN JWT REFRESH 前缀
     */
    AUTH_ADMIN_JWT_REFRESH_PREFIX("auth:admin:jwt:refresh:"),
    /**
     * AUTH ADMIN JWT BUFFER 格式
     * auth:admin:jwt:buffer:{username}:{jwt}
     */
    AUTH_ADMIN_JWT_BUFFER_FORMAT("auth:admin:jwt:buffer:{}:{}"),
    /**
     * AUTH ADMIN JWT BUFFER 前缀
     */
    AUTH_ADMIN_JWT_BUFFER_PREFIX("auth:admin:jwt:buffer:"),
    /**
     * AUTH USER JWT ACCESS 格式
     * auth:user:jwt:access{mobile}:{jwt}
     */
    AUTH_USER_JWT_ACCESS_FORMAT("auth:user:jwt:access:{}:{}"),
    /**
     * AUTH USER JWT ACCESS 前缀
     */
    AUTH_USER_JWT_ACCESS_PREFIX("auth:user:jwt:access:"),
    /**
     * AUTH USER JWT REFRESH 格式
     * auth:user:jwt:refresh:{mobile}:{jwt}
     */
    AUTH_USER_JWT_REFRESH_FORMAT("auth:user:jwt:refresh:{}:{}"),
    /**
     * AUTH USER JWT REFRESH 前缀
     */
    AUTH_USER_JWT_REFRESH_PREFIX("auth:user:jwt:refresh:"),
    /**
     * AUTH USER JWT BUFFER 格式
     * auth:user:jwt:buffer:{mobile}:{jwt}
     */
    AUTH_USER_JWT_BUFFER_FORMAT("auth:user:jwt:buffer:{}:{}"),
    /**
     * AUTH USER JWT BUFFER 前缀
     */
    AUTH_USER_JWT_BUFFER_PREFIX("auth:user:jwt:buffer:");

    private final String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
