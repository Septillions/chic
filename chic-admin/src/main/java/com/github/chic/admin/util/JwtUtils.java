package com.github.chic.admin.util;

import cn.hutool.core.convert.Convert;
import com.github.chic.admin.component.security.entity.JwtAdminDetails;
import com.github.chic.common.component.constant.BaseApiCodeEnum;
import com.github.chic.common.component.exception.BaseException;
import com.github.chic.common.config.JwtProps;
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
    public static String generateAccessToken(JwtAdminDetails jwtAdminDetails, Date jwtExpireTime) {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put("aud", jwtAdminDetails.getId());
        claims.put("sub", jwtAdminDetails.getUsername());
        return generateToken(claims, jwtExpireTime);
    }

    /**
     * 生成 RefreshToken
     */
    public static String generateRefreshToken(JwtAdminDetails jwtAdminDetails, Date jwtExpireTime) {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put("aud", jwtAdminDetails.getId());
        claims.put("sub", jwtAdminDetails.getUsername());
        return generateToken(claims, jwtExpireTime);
    }

    /**
     * 生成 Token
     */
    public static String generateToken(Map<String, Object> claims, Date expiration) {
        // 生成JWT
        return Jwts.builder()
                .setClaims(claims)
                // 签发时间
                .setIssuedAt(new Date())
                // 失效时间
                .setExpiration(expiration)
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
            throw new BaseException(BaseApiCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new BaseException(BaseApiCodeEnum.TOKEN_INVALID);
        }
    }

    /**
     * 获取 Token 中的管理员ID
     */
    public static Long getAdminId(String token) {
        String adminId = getClaims(token).getAudience();
        return Convert.toLong(adminId);
    }

    /**
     * 获取 Token 中的用户名
     */
    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }
}
