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
     * JWT AccessToken 过期时间
     */
    public static Long accessTokenExpireTime;
    /**
     * JWT RefreshToken 过期时间
     */
    public static Long refreshTokenExpireTime;
    /**
     * JWT BufferToken 过期时间
     */
    public static Long bufferTokenExpireTime;

    public void setSecret(String secret) {
        JwtProps.secret = secret;
    }

    public void setHeader(String header) {
        JwtProps.header = header;
    }

    public void setAccessTokenExpireTime(Long accessTokenExpireTime) {
        JwtProps.accessTokenExpireTime = accessTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(Long refreshTokenExpireTime) {
        JwtProps.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public void setBufferTokenExpireTime(Long bufferTokenExpireTime) {
        JwtProps.bufferTokenExpireTime = bufferTokenExpireTime;
    }
}
