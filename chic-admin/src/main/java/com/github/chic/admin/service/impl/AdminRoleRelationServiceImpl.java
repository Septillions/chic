package com.github.chic.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.service.AdminRoleRelationService;
import com.github.chic.entity.AdminRoleRelation;
import com.github.chic.mapper.AdminRoleRelationMapper;
import org.springframework.stereotype.Service;

/**
 * 管理员与角色关联表 服务实现类
 */
@Service
public class AdminRoleRelationServiceImpl extends ServiceImpl<AdminRoleRelationMapper, AdminRoleRelation> implements AdminRoleRelationService {

    @Override
    public void deleteByAdminId(Long adminId) {
        QueryWrapper<AdminRoleRelation> qw = new QueryWrapper<>();
        qw.lambda().eq(AdminRoleRelation::getAdminId, adminId);
        this.remove(qw);
    }
}
