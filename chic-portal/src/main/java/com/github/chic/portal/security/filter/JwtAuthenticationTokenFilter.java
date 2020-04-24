package com.github.chic.portal.security.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.chic.common.config.AuthProps;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.entity.api.ApiCodeEnum;
import com.github.chic.common.entity.constant.RedisKeyEnum;
import com.github.chic.common.entity.dto.RedisJwtUserDTO;
import com.github.chic.common.exception.AuthException;
import com.github.chic.common.service.RedisService;
import com.github.chic.common.util.ServletUtils;
import com.github.chic.portal.security.entity.JwtUserDetails;
import com.github.chic.portal.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

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
     * 自定义认证配置类
     */
    @Autowired
    private AuthProps authProps;
    /**
     * Redis 业务类
     */
    @Autowired
    private RedisService redisService;
    /**
     * Security 用户服务类
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获得TokenHeader
        String token = request.getHeader(JwtProps.header);
        // 获取请求头中JWT的Token
        if (!StrUtil.isEmpty(token)) {
            // 获取
            String mobile = null;
            try {
                mobile = JwtUtils.getMobile(token);
            } catch (AuthException e) {
                ServletUtils.writeJson(response, e.getErrCode(), e.getErrMsg());
                return;
            }
            // Redis有效控制
            String redisJwtKey = StrUtil.format(RedisKeyEnum.AUTH_JWT_USER_FORMAT.getKey(), mobile, token);
            RedisJwtUserDTO redisJwtUserDTO = (RedisJwtUserDTO) redisService.get(redisJwtKey);
            if (redisJwtUserDTO == null) {
                ServletUtils.writeJson(response, ApiCodeEnum.INVALID.getCode(), "Token失效");
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
     * 不需要认证的接口不执行过滤
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
                ignores.addAll(authProps.getIgnores().getGet());
                break;
            case POST:
                ignores.addAll(authProps.getIgnores().getPost());
                break;
            case PUT:
                ignores.addAll(authProps.getIgnores().getPut());
                break;
            case DELETE:
                ignores.addAll(authProps.getIgnores().getDelete());
                break;
            default:
                break;
        }
        ignores.addAll(authProps.getIgnores().getPattern());
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
