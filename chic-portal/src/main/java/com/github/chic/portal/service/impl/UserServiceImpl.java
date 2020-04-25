package com.github.chic.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.entity.constant.RedisKeyEnum;
import com.github.chic.common.entity.dto.RedisJwtUserDTO;
import com.github.chic.common.exception.AuthException;
import com.github.chic.common.service.RedisService;
import com.github.chic.common.util.ServletUtils;
import com.github.chic.entity.Permission;
import com.github.chic.entity.Role;
import com.github.chic.entity.User;
import com.github.chic.portal.mapper.PermissionMapper;
import com.github.chic.portal.mapper.RoleMapper;
import com.github.chic.portal.mapper.UserMapper;
import com.github.chic.portal.model.param.LoginParam;
import com.github.chic.portal.model.param.RegisterParam;
import com.github.chic.portal.security.entity.JwtUserDetails;
import com.github.chic.portal.service.UserService;
import com.github.chic.portal.util.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
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
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getMobile, registerParam.getMobile());
        Integer count = userMapper.selectCount(wrapper);
        if (count > 0) {
            throw new AuthException(1005, "手机号已经注册");
        }
        // 创建用户
        User user = new User();
        user.setUsername(registerParam.getMobile());
        user.setMobile(registerParam.getMobile());
        user.setPassword(passwordEncoder.encode(registerParam.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public String login(LoginParam loginParam) {
        // 获取用户
        User user = getByMobile(loginParam.getMobile());
        if (user == null) {
            throw new AuthException(1003, "该帐号不存在(The account does not exist)");
        }
        if (!passwordEncoder.matches(loginParam.getPassword(), user.getPassword())) {
            throw new AuthException(1003, "帐号或密码错误(Account or Password Error)");
        }
        // Security
        JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(loginParam.getMobile());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT
        String jwt = JwtUtils.generateToken(jwtUserDetails);
        // Redis
        String redisJwtKey = StrUtil.format(RedisKeyEnum.AUTH_JWT_USER_FORMAT.getKey(), user.getUsername(), jwt);
        UserAgent ua = ServletUtils.getUserAgent();
        RedisJwtUserDTO redisJwtUserDTO = new RedisJwtUserDTO();
        redisJwtUserDTO.setMobile(user.getMobile());
        redisJwtUserDTO.setJwt(jwt);
        redisJwtUserDTO.setOs(ua.getOs().toString());
        redisJwtUserDTO.setPlatform(ua.getPlatform().toString());
        redisJwtUserDTO.setIp(ServletUtils.getIpAddress());
        redisJwtUserDTO.setLoginTime(LocalDateTime.now());
        redisService.set(redisJwtKey, redisJwtUserDTO, JwtProps.expiration);
        return jwt;
    }

    @Override
    public void logout(String token) {
        String mobile = JwtUtils.getMobile(token);
        String redisJwtKey = StrUtil.format(RedisKeyEnum.AUTH_JWT_USER_FORMAT.getKey(), mobile, token);
        redisService.delete(redisJwtKey);
    }

    @Override
    public User getByMobile(String mobile) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getMobile, mobile);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<Role> listRoleByUserId(Integer userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }

    @Override
    public List<Permission> listPermissionByUserId(Integer userId) {
        return permissionMapper.selectPermissionListByUserId(userId);
    }
}
