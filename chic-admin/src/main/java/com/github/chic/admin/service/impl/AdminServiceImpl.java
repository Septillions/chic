package com.github.chic.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.common.config.JwtProps;
import com.github.chic.admin.mapper.AdminMapper;
import com.github.chic.admin.mapper.PermissionMapper;
import com.github.chic.admin.mapper.RoleMapper;
import com.github.chic.common.entity.constant.RedisKeyEnum;
import com.github.chic.common.entity.dto.RedisJwtAdminDTO;
import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.security.entity.JwtAdminDetails;
import com.github.chic.admin.service.AdminService;
import com.github.chic.admin.util.JwtUtils;
import com.github.chic.common.exception.AuthException;
import com.github.chic.common.service.RedisService;
import com.github.chic.common.util.ServletUtils;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
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
    @Resource
    private RedisService redisService;

    @Override
    public void register(RegisterParam registerParam) {
        // 检查是否有相同用户名
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getUsername, registerParam.getUsername());
        Integer count = adminMapper.selectCount(wrapper);
        if (count > 0) {
            throw new AuthException(1005, "用户名已经注册");
        }
        // 创建用户
        Admin admin = new Admin();
        admin.setUsername(registerParam.getUsername());
        admin.setPassword(passwordEncoder.encode(registerParam.getPassword()));
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.insert(admin);
    }

    @Override
    public String login(LoginParam loginParam) {
        // 获取用户
        Admin admin = getByUsername(loginParam.getUsername());
        if (admin == null) {
            throw new AuthException(1004, "该帐号不存在(The account does not exist)");
        }
        if (!passwordEncoder.matches(loginParam.getPassword(), admin.getPassword())) {
            throw new AuthException(1004, "帐号或密码错误(Account or Password Error)");
        }
        // Security
        JwtAdminDetails jwtAdminDetails = (JwtAdminDetails) userDetailsService.loadUserByUsername(loginParam.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtAdminDetails, null, jwtAdminDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT
        String jwt = JwtUtils.generateToken(jwtAdminDetails);
        // Redis
        String redisJwtKey = StrUtil.format(RedisKeyEnum.AUTH_JWT_ADMIN_FORMAT.getKey(), admin.getUsername(), jwt);
        UserAgent ua = ServletUtils.getUserAgent();
        RedisJwtAdminDTO redisJwtAdminDTO = new RedisJwtAdminDTO();
        redisJwtAdminDTO.setUsername(admin.getUsername());
        redisJwtAdminDTO.setJwt(jwt);
        redisJwtAdminDTO.setOs(ua.getOs().toString());
        redisJwtAdminDTO.setPlatform(ua.getPlatform().toString());
        redisJwtAdminDTO.setIp(ServletUtils.getIpAddress());
        redisJwtAdminDTO.setLoginTime(LocalDateTime.now());
        redisService.set(redisJwtKey, redisJwtAdminDTO, JwtProps.expiration);
        return jwt;
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
