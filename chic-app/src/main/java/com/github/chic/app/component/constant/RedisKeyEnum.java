package com.github.chic.app.component.constant;

/**
 * Redis Key Cache 缓存枚举类
 */
public enum RedisKeyEnum {
    /**
     * APP CACHE USER 前缀
     * app:cache:user:{mobile}
     */
    APP_CACHE_USER_FORMAT("app:cache:user:{}"),

    /**
     * 短信验证码限制 前缀
     * app:aliyunsms:limit:{mobile}
     */
    APP_ALIYUNSMS_LIMIT_FORMAT("app:aliyunsms:limit:{}"),

    /**
     * 登录短信验证码 前缀
     * app:auth:smscode:{mobile}
     */
    APP_SMSCODE_USER_AUTH_FORMAT("app:smscode:user:auth:{}"),
    ;
    private final String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
