package com.github.chic.portal.component.constant;

/**
 * Redis Key Cache 缓存枚举类
 */
public enum RedisKeyCacheEnum {
    /**
     * PORTAL CACHE USER 格式
     * portal:cache:user:{mobile}
     * User
     */
    PORTAL_CACHE_USER_FORMAT("portal:cache:user:{}"),
    /**
     * PORTAL CACHE USER 前缀
     */
    PORTAL_CACHE_USER_PREFIX("portal:cache:user:");
    private final String key;

    RedisKeyCacheEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
