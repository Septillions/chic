package com.github.chic.portal.config;

import com.github.chic.common.config.AuthProps;
import com.github.chic.portal.security.component.RestAccessDeniedHandler;
import com.github.chic.portal.security.component.RestAuthenticationEntryPoint;
import com.github.chic.portal.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义认证配置类
     */
    @Resource
    private AuthProps authProps;
    /**
     * 自定义未登录处理类
     */
    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    /**
     * 自定义无权限处理类
     */
    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;
    /**
     * Security用户服务类
     */
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 登录登出由自己实现
        httpSecurity.formLogin().disable()
                .logout().disable();
        // 认证请求
        httpSecurity.authorizeRequests()
                // 放行druid监控资源
                .antMatchers("/druid/**").permitAll()
                // 放行swagger文档资源
                .antMatchers("/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/swagger-resources/**").permitAll()
                // 所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        // 取消跨站请求伪造防护
        httpSecurity.csrf().disable().cors();
        // 基于token不需要session
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加JWT过滤器
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 配置异常自定义处理类
        httpSecurity.exceptionHandling()
                // 未登录自定义处理类
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                // 无权限自定义处理类
                .accessDeniedHandler(restAccessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        // 忽略 GET
        authProps.getIgnore().getGet().forEach(url -> webSecurity.ignoring().antMatchers(HttpMethod.GET, url));
        // 忽略 POST
        authProps.getIgnore().getPost().forEach(url -> webSecurity.ignoring().antMatchers(HttpMethod.POST, url));
        // 忽略 PUT
        authProps.getIgnore().getPut().forEach(url -> webSecurity.ignoring().antMatchers(HttpMethod.PUT, url));
        // 忽略 DELETE
        authProps.getIgnore().getDelete().forEach(url -> webSecurity.ignoring().antMatchers(HttpMethod.DELETE, url));
        // 按照请求格式忽略
        authProps.getIgnore().getPattern().forEach(url -> webSecurity.ignoring().antMatchers(url));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
