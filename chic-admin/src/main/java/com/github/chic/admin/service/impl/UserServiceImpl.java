package com.github.chic.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.mapper.UserMapper;
import com.github.chic.admin.model.param.UserParam;
import com.github.chic.admin.service.UserService;
import com.github.chic.common.entity.param.PageParam;
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
    public List<User> listByParam(PageParam pageParam, UserParam userParam) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(userParam.getUsername())) {
            wrapper.lambda().eq(User::getUsername, userParam.getMobile());
        }
        if (StrUtil.isNotBlank(userParam.getUsername())) {
            wrapper.lambda().eq(User::getMobile, userParam.getMobile());
        }
        PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        return userMapper.selectList(wrapper);
    }
}
