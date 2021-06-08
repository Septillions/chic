package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<Permission> listByAdminId(Long adminId);
}
