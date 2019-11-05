package com.github.chic.admin.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security 用户实现
 */
@Data
public class JwtAdminDetails implements UserDetails {
    /**
     * ID
     */
    private Integer adminId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色
     */
    private Collection<GrantedAuthority> authorities;
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
