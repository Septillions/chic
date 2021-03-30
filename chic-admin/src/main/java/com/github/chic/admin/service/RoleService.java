package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> listByAdminId(Integer adminId);
}
