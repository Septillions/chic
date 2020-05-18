package com.github.chic.portal.util;

import com.github.chic.common.config.JwtProps;
import com.github.chic.common.entity.api.ApiCodeEnum;
import com.github.chic.common.exception.AuthException;
import com.github.chic.portal.security.entity.JwtUserDetails;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 */
public class JwtUtils {
    /**
     * 生成 AccessToken
     */
    public static String generateAccessToken(JwtUserDetails jwtUserDetails) {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put("sub", jwtUserDetails.getMobile());
        return generateToken(claims, JwtProps.accessTokenExpireTime);
    }

    /**
     * 生成 RefreshToken
     */
    public static String generateRefreshToken(JwtUserDetails jwtUserDetails) {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put("sub", jwtUserDetails.getMobile());
        return generateToken(claims, JwtProps.refreshTokenExpireTime);
    }

    /**
     * 生成 Token
     */
    public static String generateToken(Map<String, Object> claims, Long expiration) {
        // 生成 JWT
        return Jwts.builder()
                .setClaims(claims)
                // 签发时间
                .setIssuedAt(new Date())
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtProps.secret)
                .compact();
    }

    /**
     * 解析 Token
     */
    public static Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(JwtProps.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException(ApiCodeEnum.INVALID.getCode(), "Token 过期");
        } catch (JwtException e) {
            throw new AuthException(ApiCodeEnum.INVALID.getCode(), "Token 无效");
        }
    }

    /**
     * 获取Token中的手机号
     */
    public static String getMobile(String token) {
        return getClaims(token).getSubject();
    }
}
