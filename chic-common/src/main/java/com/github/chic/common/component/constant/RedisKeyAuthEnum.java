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
     * AUTH ADMIN CAPTCHA 格式
     * admin:auth:captcha:{uuid}
     */
    ADMIN_AUTH_CAPTCHA_FORMAT("admin:auth:captcha:{}"),
    /**
     * AUTH ADMIN CAPTCHA 前缀
     */
    ADMIN_AUTH_CAPTCHA_PREFIX("admin:auth:captcha:"),

    /**
     * PORTAL AUTH JWT ACCESS 格式
     * portal:auth:jwt:access{mobile}:{jwt}
     */
    PORTAL_AUTH_JWT_ACCESS_FORMAT("portal:auth:jwt:access:{}:{}"),
    /**
     * PORTAL AUTH JWT ACCESS 前缀
     */
    PORTAL_AUTH_JWT_ACCESS_PREFIX("portal:auth:jwt:access:"),
    /**
     * PORTAL AUTH JWT REFRESH 格式
     * portal:auth:jwt:refresh:{mobile}:{jwt}
     */
    PORTAL_AUTH_JWT_REFRESH_FORMAT("portal:auth:jwt:refresh:{}:{}"),
    /**
     * PORTAL AUTH JWT REFRESH 前缀
     */
    PORTAL_AUTH_JWT_REFRESH_PREFIX("portal:auth:jwt:refresh:"),
    /**
     * PORTAL AUTH JWT BUFFER 格式
     * portal:auth:jwt:buffer:{mobile}:{jwt}
     */
    PORTAL_AUTH_JWT_BUFFER_FORMAT("portal:auth:jwt:buffer:{}:{}"),
    /**
     * PORTAL AUTH JWT BUFFER 前缀
     */
    PORTAL_AUTH_JWT_BUFFER_PREFIX("portal:auth:jwt:buffer:");

    private final String key;

    RedisKeyAuthEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
