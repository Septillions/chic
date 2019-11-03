package com.github.chic.admin.util;

import com.github.chic.admin.config.JwtConfig;
import com.github.chic.admin.security.entity.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * 根据用户生成Token
     */
    public static String generateToken(JwtUserDetails jwtUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", jwtUserDetails.getUsername());
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
                .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.expiration * 1000))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtConfig.secret)
                .compact();
    }

    /**
     * 解析Token
     */
    public static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(JwtConfig.secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取Token中的用户名
     */
    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }
}
