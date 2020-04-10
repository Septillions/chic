package com.github.chic.admin.security.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.chic.admin.config.AuthConfig;
import com.github.chic.admin.config.JwtConfig;
import com.github.chic.admin.model.constant.RedisKeyEnum;
import com.github.chic.admin.model.dto.RedisJwtDTO;
import com.github.chic.admin.security.entity.JwtAdminDetails;
import com.github.chic.admin.util.JwtUtils;
import com.github.chic.common.entity.api.ApiCodeEnum;
import com.github.chic.common.exception.AuthException;
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
     * 自定义认证配置类
     */
    @Resource
    private AuthConfig authConfig;
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
        // 获得TokenHeader
        String token = request.getHeader(JwtConfig.tokenHeader);
        // 获取请求头中JWT的Token
        if (!StrUtil.isEmpty(token)) {
            // 获取用户名
            String username = null;
            try {
                username = JwtUtils.getUsername(token);
            } catch (AuthException e) {
                ServletUtils.writeJson(response, e.getErrCode(), e.getErrMsg());
                return;
            }
            // Redis有效控制
            String redisJwtKey = StrUtil.format(RedisKeyEnum.AUTH_JWT_FORMAT.getKey(), username, token);
            RedisJwtDTO redisJwtDTO = (RedisJwtDTO) redisService.get(redisJwtKey);
            if (redisJwtDTO == null) {
                ServletUtils.writeJson(response, ApiCodeEnum.INVALID.getCode(), "Token失效");
                return;
            }
            // 认证
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                JwtAdminDetails jwtAdminDetails = (JwtAdminDetails) userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtAdminDetails, jwtAdminDetails.getPassword(), jwtAdminDetails.getAuthorities());
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
                ignores.addAll(authConfig.getIgnores().getGet());
                break;
            case POST:
                ignores.addAll(authConfig.getIgnores().getPost());
                break;
            case PUT:
                ignores.addAll(authConfig.getIgnores().getPut());
                break;
            case DELETE:
                ignores.addAll(authConfig.getIgnores().getDelete());
                break;
            default:
                break;
        }
        ignores.addAll(authConfig.getIgnores().getPattern());
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
