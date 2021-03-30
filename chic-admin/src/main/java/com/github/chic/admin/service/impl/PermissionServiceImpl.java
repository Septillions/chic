package com.github.chic.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.mapper.PermissionMapper;
import com.github.chic.admin.service.PermissionService;
import com.github.chic.entity.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public List<Permission> listByAdminId(Integer adminId) {
        return this.baseMapper.listByAdminId(adminId);
    }
}
