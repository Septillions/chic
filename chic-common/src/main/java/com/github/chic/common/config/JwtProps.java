package com.github.chic.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置类
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProps {
    /**
     * JWT 加解密使用的密钥
     */
    public static String secret;
    /**
     * JWT 存储的请求头
     */
    public static String header;
    /**
     * JWT 超期限时间
     */
    public static Long expiration;

    public void setSecret(String secret) {
        JwtProps.secret = secret;
    }

    public void setHeader(String header) {
        JwtProps.header = header;
    }

    public void setExpiration(Long expiration) {
        JwtProps.expiration = expiration;
    }
}
