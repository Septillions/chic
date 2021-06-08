package com.github.chic.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.entity.User;
import com.github.chic.portal.mapper.UserMapper;
import com.github.chic.portal.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getByMobile(String mobile) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.lambda().eq(User::getMobile, mobile);
        return userMapper.selectOne(qw);
    }
}
