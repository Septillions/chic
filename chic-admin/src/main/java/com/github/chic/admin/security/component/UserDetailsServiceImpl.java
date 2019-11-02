package com.github.chic.admin.security.component;

import com.github.chic.admin.security.entity.JwtUserDetails;
import com.github.chic.admin.service.AdminService;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security 用户的业务实现
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private AdminService adminService;

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户信息
        Admin admin = adminService.getByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("无效用户");
        }
        // 获取角色列表
        List<Role> roleList = adminService.listRoleByAdminId(admin.getAdminId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()));
        }
        // 构建Security用户
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        jwtUserDetails.setAdminId(admin.getAdminId());
        jwtUserDetails.setUsername(admin.getUsername());
        jwtUserDetails.setPassword(admin.getPassword());
        jwtUserDetails.setAuthorities(authorities);
        return jwtUserDetails;
    }
}
