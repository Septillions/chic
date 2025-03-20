package com.github.chic.app.component.security.filter;

import cn.hutool.core.util.StrUtil;
import com.github.chic.app.component.constant.ApiCodeEnum;
import com.github.chic.app.component.exception.ApiException;
import com.github.chic.app.component.security.entity.JwtUserDetails;
import com.github.chic.app.util.JwtUtils;
import com.github.chic.common.component.constant.BaseRedisKeyEnum;
import com.github.chic.common.component.props.JwtProps;
import com.github.chic.common.model.dto.RedisJwtUserDTO;
import com.github.chic.common.service.RedisService;
import com.github.chic.common.util.ServletUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    /**
     * Redis 业务类
     */
    @Resource
    private RedisService redisService;
    /**
     * Security 用户服务类
     */
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取 Token
        String token = request.getHeader(JwtProps.header);
        if (!StrUtil.isEmpty(token)) {
            // 获取手机号
            String mobile = null;
            try {
                mobile = JwtUtils.getMobile(token);
            } catch (ApiException e) {
                ServletUtils.writeJson(response, e.getErrCode(), e.getErrMsg());
                return;
            }
            // Redis 有效控制
            String redisJwtKey = StrUtil.format(BaseRedisKeyEnum.APP_AUTH_JWT_ACCESS_FORMAT.getKey(), mobile, token);
            RedisJwtUserDTO redisJwtUserDTO = (RedisJwtUserDTO) redisService.get(redisJwtKey);
            if (redisJwtUserDTO == null) {
                ServletUtils.writeJson(response, ApiCodeEnum.AUTH_ACCESS_TOKEN_EXPIRED.getCode(), ApiCodeEnum.AUTH_ACCESS_TOKEN_EXPIRED.getMsg());
                return;
            }
            // 认证
            if (mobile != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(mobile);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUserDetails, jwtUserDetails.getPassword(), jwtUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
