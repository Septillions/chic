package com.github.chic.app.component.constant;

/**
 * Redis Key Cache 缓存枚举类
 */
public enum RedisKeyCacheEnum {
    /**
     * APP CACHE USER 格式
     * app:cache:user:{mobile}
     * User
     */
    APP_CACHE_USER_FORMAT("app:cache:user:{}"),
    /**
     * APP CACHE USER 前缀
     */
    APP_CACHE_USER_PREFIX("app:cache:user:");
    private final String key;

    RedisKeyCacheEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
