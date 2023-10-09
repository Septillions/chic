package com.github.chic.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.model.query.UserQuery;
import com.github.chic.admin.service.UserService;
import com.github.chic.common.model.param.PageQuery;
import com.github.chic.entity.User;
import com.github.chic.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> pageQuery(PageQuery page, UserQuery query) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(query.getUsername())) {
            qw.lambda().like(User::getUsername, query.getUsername());
        }
        if (StrUtil.isNotBlank(query.getMobile())) {
            qw.lambda().like(User::getMobile, query.getMobile());
        }
        PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        return super.list(qw);
    }
}
