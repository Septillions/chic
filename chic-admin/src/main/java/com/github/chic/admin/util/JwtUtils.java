package com.github.chic.admin.util;

import com.github.chic.common.config.JwtProps;
import com.github.chic.admin.security.entity.JwtAdminDetails;
import com.github.chic.common.entity.api.ApiCodeEnum;
import com.github.chic.common.exception.AuthException;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 */
public class JwtUtils {
    /**
     * 根据用户生成Token
     */
    public static String generateToken(JwtAdminDetails jwtAdminDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", jwtAdminDetails.getUsername());
        return generateToken(claims);
    }

    /**
     * 根据负载生成Token
     */
    public static String generateToken(Map<String, Object> claims) {
        // 生成JWT
        return Jwts.builder()
                .setClaims(claims)
                // 签发时间
                .setIssuedAt(new Date())
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JwtProps.expiration * 1000))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtProps.secret)
                .compact();
    }

    /**
     * 解析Token
     */
    public static Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(JwtProps.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException(ApiCodeEnum.INVALID.getCode(), "Token过期");
        } catch (JwtException e) {
            throw new AuthException(ApiCodeEnum.INVALID.getCode(), "Token无效");
        }
    }

    /**
     * 获取Token中的用户名
     */
    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }
}
