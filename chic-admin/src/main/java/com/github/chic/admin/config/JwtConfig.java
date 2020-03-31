package com.github.chic.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置类
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    /**
     * JWT 加解密使用的密钥
     */
    public static String secret;
    /**
     * JWT 存储的请求头
     */
    public static String tokenHeader;
    /**
     * JWT 超期限时间
     */
    public static Integer expiration;

    public void setSecret(String secret) {
        JwtConfig.secret = secret;
    }

    public void setTokenHeader(String tokenHeader) {
        JwtConfig.tokenHeader = tokenHeader;
    }

    public void setExpiration(Integer expiration) {
        JwtConfig.expiration = expiration;
    }
}
