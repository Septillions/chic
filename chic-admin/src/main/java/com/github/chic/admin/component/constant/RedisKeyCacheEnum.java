package com.github.chic.admin.component.constant;

/**
 * Redis Key Cache 缓存枚举类
 */
public enum RedisKeyCacheEnum {
    /**
     * ADMIN CACHE ADMIN 格式
     * admin:cache:admin:{username}
     * Admin
     */
    ADMIN_CACHE_ADMIN_FORMAT("admin:cache:admin:{}"),
    /**
     * ADMIN CACHE ADMIN 前缀
     */
    ADMIN_CACHE_ADMIN_PREFIX("admin:cache:admin:"),
    /**
     * ADMIN CACHE ROLE 格式
     * admin:cache:role:{adminId}
     * List<Role>
     */
    ADMIN_CACHE_ROLE_FORMAT("admin:cache:role:{}"),
    /**
     * ADMIN CACHE ROLE 前缀
     */
    ADMIN_CACHE_ROLE_PREFIX("admin:cache:role:"),
    /**
     * ADMIN CACHE PERMISSION 格式
     * admin:cache:permission:{adminId}
     * List<Permission>
     */
    ADMIN_CACHE_PERMISSION_FORMAT("admin:cache:permission:{}"),
    /**
     * ADMIN CACHE PERMISSION 前缀
     */
    ADMIN_CACHE_PERMISSION_PREFIX("admin:cache:permission:");

    private final String key;

    RedisKeyCacheEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
