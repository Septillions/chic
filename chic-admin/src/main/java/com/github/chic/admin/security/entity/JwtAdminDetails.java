package com.github.chic.admin.security.entity;

import cn.hutool.core.bean.BeanUtil;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public static JwtAdminDetails create(Admin admin, List<Role> roleList) {
        List<SimpleGrantedAuthority> authorities = roleList.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()))
                .collect(Collectors.toList());
        JwtAdminDetails jwtAdminDetails = new JwtAdminDetails();
        BeanUtil.copyProperties(admin, jwtAdminDetails);
        jwtAdminDetails.setAuthorities(authorities);
        return jwtAdminDetails;
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
