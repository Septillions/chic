package com.github.chic.admin.component.constant;

/**
 * Redis Key Cache 缓存枚举类
 */
public enum RedisKeyEnum {
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
     * ADMIN CACHE MENU 格式
     * admin:cache:menu:{adminId}
     * List<Menu>
     */
    ADMIN_CACHE_MENU_FORMAT("admin:cache:menu:{}"),
    /**
     * ADMIN CACHE MENU 前缀
     */
    ADMIN_CACHE_MENU_PREFIX("admin:cache:menu:"),
    ;

    private final String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
