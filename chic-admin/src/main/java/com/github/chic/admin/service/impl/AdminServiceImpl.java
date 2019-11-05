package com.github.chic.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.chic.admin.mapper.AdminMapper;
import com.github.chic.admin.mapper.PermissionMapper;
import com.github.chic.admin.mapper.RoleMapper;
import com.github.chic.admin.model.dto.AdminParam;
import com.github.chic.admin.security.entity.JwtAdminDetails;
import com.github.chic.admin.service.AdminService;
import com.github.chic.admin.util.JwtUtils;
import com.github.chic.common.exception.AuthException;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Permission;
import com.github.chic.entity.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(AdminParam adminParam) {
        // 检查是否有相同用户名
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getUsername, adminParam.getUsername());
        Integer count = adminMapper.selectCount(wrapper);
        if (count > 0) {
            throw new AuthException(1004, "用户名已经注册");
        }
        // 创建用户
        Admin admin = new Admin();
        BeanUtil.copyProperties(adminParam, admin);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.insert(admin);
    }

    @Override
    public String login(AdminParam adminParam) {
        // 获取用户
        Admin admin = getByUsername(adminParam.getUsername());
        if (admin == null) {
            throw new AuthException(1003, "该帐号不存在(The account does not exist)");
        }
        if (!passwordEncoder.matches(adminParam.getPassword(), admin.getPassword())) {
            throw new AuthException(1003, "帐号或密码错误(Account or Password Error)");
        }
        // Security
        JwtAdminDetails jwtAdminDetails = (JwtAdminDetails) userDetailsService.loadUserByUsername(adminParam.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtAdminDetails, null, jwtAdminDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return JwtUtils.generateToken(jwtAdminDetails);
    }

    @Override
    public Admin getByUsername(String username) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getUsername, username);
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public List<Role> listRoleByAdminId(Integer adminId) {
        return roleMapper.selectRoleListByAdminId(adminId);
    }

    @Override
    public List<Permission> listPermissionByAdminId(Integer adminId) {
        return permissionMapper.selectPermissionListByAdminId(adminId);
    }
}
