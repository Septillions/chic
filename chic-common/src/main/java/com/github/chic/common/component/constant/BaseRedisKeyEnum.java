package com.github.chic.common.component.constant;

/**
 * 全局 Redis Key 枚举类
 */
public enum BaseRedisKeyEnum {
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
     * AUTH ADMIN CAPTCHA 格式
     * admin:auth:captcha:{uuid}
     */
    ADMIN_AUTH_CAPTCHA_FORMAT("admin:auth:captcha:{}"),
    /**
     * AUTH ADMIN CAPTCHA 前缀
     */
    ADMIN_AUTH_CAPTCHA_PREFIX("admin:auth:captcha:"),

    /**
     * APP AUTH JWT ACCESS 格式
     * app:auth:jwt:access{mobile}:{jwt}
     */
    APP_AUTH_JWT_ACCESS_FORMAT("app:auth:jwt:access:{}:{}"),
    /**
     * APP AUTH JWT ACCESS 前缀
     */
    APP_AUTH_JWT_ACCESS_PREFIX("app:auth:jwt:access:"),
    /**
     * APP AUTH JWT REFRESH 格式
     * app:auth:jwt:refresh:{mobile}:{jwt}
     */
    APP_AUTH_JWT_REFRESH_FORMAT("app:auth:jwt:refresh:{}:{}"),
    /**
     * APP AUTH JWT REFRESH 前缀
     */
    APP_AUTH_JWT_REFRESH_PREFIX("app:auth:jwt:refresh:");

    private final String key;

    BaseRedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
