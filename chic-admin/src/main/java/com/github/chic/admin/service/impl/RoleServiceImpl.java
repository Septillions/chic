package com.github.chic.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.mapper.RoleMapper;
import com.github.chic.admin.service.RoleService;
import com.github.chic.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<Role> listByAdminId(Integer adminId) {
        return this.baseMapper.listByAdminId(adminId);
    }
}
