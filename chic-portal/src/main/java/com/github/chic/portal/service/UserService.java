package com.github.chic.portal.service;

import com.github.chic.entity.User;
import com.github.chic.portal.model.dto.LoginParam;

public interface UserService {
    String login(LoginParam loginParam);

    User getByMobile(String mobile);
}
