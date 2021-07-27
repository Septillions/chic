package com.github.chic.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Cache 配置参数类
 */
@Component
@ConfigurationProperties(prefix = "cache")
public class CacheProps {
    /**
     * 默认过期时间 默认7天(60*60*24*7 604800)
     */
    public static Long defaultExpireTime = 604800L;

    public Long getDefaultExpireTime() {
        return defaultExpireTime;
    }

    public void setDefaultExpireTime(Long defaultExpireTime) {
        CacheProps.defaultExpireTime = defaultExpireTime;
    }
}
