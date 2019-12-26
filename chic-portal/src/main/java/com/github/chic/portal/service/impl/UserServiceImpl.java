package com.github.chic.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.chic.common.exception.AuthException;
import com.github.chic.entity.User;
import com.github.chic.portal.mapper.UserMapper;
import com.github.chic.portal.model.dto.LoginParam;
import com.github.chic.portal.security.entity.JwtUserDetails;
import com.github.chic.portal.service.UserService;
import com.github.chic.portal.util.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;

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
        return JwtUtils.generateToken(jwtUserDetails);
    }

    @Override
    public User getByMobile(String mobile) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getMobile, mobile);
        return userMapper.selectOne(wrapper);
    }
}
