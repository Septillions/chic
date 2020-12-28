package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.admin.model.query.UserQuery;
import com.github.chic.common.model.param.PageParam;
import com.github.chic.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> pageQuery(PageParam pageParam, UserQuery userQuery);
}
