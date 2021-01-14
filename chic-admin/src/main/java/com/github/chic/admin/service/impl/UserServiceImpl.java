package com.github.chic.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.mapper.UserMapper;
import com.github.chic.admin.model.query.UserQuery;
import com.github.chic.admin.service.UserService;
import com.github.chic.common.model.param.PageParam;
import com.github.chic.entity.User;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> pageQuery(PageParam pageParam, UserQuery userQuery) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(userQuery.getUsername())) {
            qw.lambda().eq(User::getUsername, userQuery.getUsername());
        }
        if (StrUtil.isNotBlank(userQuery.getUsername())) {
            qw.lambda().eq(User::getMobile, userQuery.getMobile());
        }
        PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        return userMapper.selectList(qw);
    }
}
