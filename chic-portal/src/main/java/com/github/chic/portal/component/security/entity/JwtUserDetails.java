package com.github.chic.portal.component.security.entity;

import cn.hutool.core.bean.BeanUtil;
import com.github.chic.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Spring Security 用户实现
 */
@Data
public class JwtUserDetails implements UserDetails {
    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 角色列表
     */
    private Collection<? extends GrantedAuthority> authorities;
    /**
     * 账户是否未过期
     */
    private boolean isAccountNonExpired = true;
    /**
     * 账户是否未锁定
     */
    private boolean isAccountNonLocked = true;
    /**
     * 证书是否未过期
     */
    private boolean isCredentialsNonExpired = true;
    /**
     * 账户是否有效
     */
    private boolean isEnabled = true;

    public static JwtUserDetails create(User user) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_PORTAL"));
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        BeanUtil.copyProperties(user, jwtUserDetails);
        jwtUserDetails.setAuthorities(authorities);
        return jwtUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
