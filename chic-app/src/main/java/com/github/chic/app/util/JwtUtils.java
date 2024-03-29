package com.github.chic.app.util;

import cn.hutool.core.convert.Convert;
import com.github.chic.app.component.security.entity.JwtUserDetails;
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
    public static String generateAccessToken(JwtUserDetails jwtUserDetails) {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put("aud", jwtUserDetails.getId());
        claims.put("sub", jwtUserDetails.getMobile());
        return generateToken(claims, JwtProps.accessTokenExpireTime);
    }

    /**
     * 生成 RefreshToken
     */
    public static String generateRefreshToken(JwtUserDetails jwtUserDetails) {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put("aud", jwtUserDetails.getId());
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
            throw new BaseException(BaseApiCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new BaseException(BaseApiCodeEnum.TOKEN_INVALID);
        }
    }

    /**
     * 获取 Token 中的用户ID
     */
    public static Long getUserId(String token) {
        String userId = getClaims(token).getAudience();
        return Convert.toLong(userId);
    }

    /**
     * 获取 Token 中的手机号
     */
    public static String getMobile(String token) {
        return getClaims(token).getSubject();
    }
}
