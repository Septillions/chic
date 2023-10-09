package com.github.chic.app.component.security.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.chic.app.component.security.entity.JwtUserDetails;
import com.github.chic.app.util.JwtUtils;
import com.github.chic.common.component.constant.BaseApiCodeEnum;
import com.github.chic.common.component.constant.BaseRedisKeyEnum;
import com.github.chic.common.component.exception.BaseException;
import com.github.chic.common.config.AuthProps;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.model.dto.RedisJwtUserDTO;
import com.github.chic.common.service.RedisService;
import com.github.chic.common.util.ServletUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * JWT 认证过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    /**
     * Auth 配置参数类
     */
    @Resource
    private AuthProps authProps;
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
            } catch (BaseException e) {
                ServletUtils.writeJson(response, e.getErrCode(), e.getErrMsg());
                return;
            }
            // Redis 有效控制
            String redisJwtKey = StrUtil.format(BaseRedisKeyEnum.APP_AUTH_JWT_ACCESS_FORMAT.getKey(), mobile, token);
            RedisJwtUserDTO redisJwtUserDTO = (RedisJwtUserDTO) redisService.get(redisJwtKey);
            if (redisJwtUserDTO == null) {
                ServletUtils.writeJson(response, BaseApiCodeEnum.INVALID.getCode(), "Token 失效");
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

    /**
     * 忽略认证的接口不执行过滤
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String method = request.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }
        Set<String> ignores = new HashSet<>();
        switch (httpMethod) {
            case GET:
                ignores.addAll(authProps.getIgnore().getGet());
                break;
            case POST:
                ignores.addAll(authProps.getIgnore().getPost());
                break;
            case PUT:
                ignores.addAll(authProps.getIgnore().getPut());
                break;
            case DELETE:
                ignores.addAll(authProps.getIgnore().getDelete());
                break;
            default:
                break;
        }
        ignores.addAll(authProps.getIgnore().getPattern());
        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }
}
