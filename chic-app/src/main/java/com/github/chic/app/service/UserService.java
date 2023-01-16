package com.github.chic.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.entity.User;

public interface UserService extends IService<User> {
    User getByMobile(String mobile);
}
