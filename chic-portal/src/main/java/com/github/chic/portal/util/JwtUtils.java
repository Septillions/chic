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
     * 根据用户生成Token
     */
    public static String generateToken(JwtUserDetails jwtUserDetails) {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put("sub", jwtUserDetails.getMobile());
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
     * 获取Token中的手机号
     */
    public static String getMobile(String token) {
        return getClaims(token).getSubject();
    }
}
