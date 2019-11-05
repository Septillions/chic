package com.github.chic.admin.security.filter;

import com.github.chic.admin.config.JwtConfig;
import com.github.chic.admin.security.entity.JwtAdminDetails;
import com.github.chic.admin.util.JwtUtils;
import com.github.chic.common.component.ResultCode;
import com.github.chic.common.util.JsonResultUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * JWT 认证过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获得TokenHeader
        String token = request.getHeader(JwtConfig.tokenHeader);
        // 获取请求头中JWT的Token
        if (!StringUtils.isEmpty(token)) {
            // 获取用户名
            String username = null;
            try {
                username = JwtUtils.getUsername(token);
            } catch (ExpiredJwtException e) {
                JsonResultUtils.responseJson(response, ResultCode.UNAUTHORIZED.getCode(), "Token过期");
                return;
            } catch (JwtException e) {
                JsonResultUtils.responseJson(response, ResultCode.UNAUTHORIZED.getCode(), "Token无效");
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
        String uri = request.getRequestURI();
        String[] antMatchers = JwtConfig.antMatchers.split(",");
        return Arrays.asList(antMatchers).contains(uri);
    }
}
