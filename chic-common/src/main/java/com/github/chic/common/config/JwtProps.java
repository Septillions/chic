package com.github.chic.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置参数类
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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        JwtProps.secret = secret;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        JwtProps.header = header;
    }

    public Long getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }

    public void setAccessTokenExpireTime(Long accessTokenExpireTime) {
        JwtProps.accessTokenExpireTime = accessTokenExpireTime;
    }

    public Long getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(Long refreshTokenExpireTime) {
        JwtProps.refreshTokenExpireTime = refreshTokenExpireTime;
    }
}
