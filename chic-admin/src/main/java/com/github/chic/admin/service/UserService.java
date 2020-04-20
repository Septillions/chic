package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.admin.model.param.UserParam;
import com.github.chic.common.entity.param.PageParam;
import com.github.chic.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> listByParam(PageParam pageParam, UserParam userParam);
}
