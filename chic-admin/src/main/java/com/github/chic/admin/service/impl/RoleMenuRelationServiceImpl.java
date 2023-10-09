package com.github.chic.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.service.RoleMenuRelationService;
import com.github.chic.entity.RoleMenuRelation;
import com.github.chic.mapper.RoleMenuRelationMapper;
import org.springframework.stereotype.Service;

/**
 * 角色与菜单关联表 服务实现类
 */
@Service
public class RoleMenuRelationServiceImpl extends ServiceImpl<RoleMenuRelationMapper, RoleMenuRelation> implements RoleMenuRelationService {

    @Override
    public void deleteByRoleId(Long roleId) {
        QueryWrapper<RoleMenuRelation> qw = new QueryWrapper<>();
        qw.lambda().eq(RoleMenuRelation::getRoleId, roleId);
        this.remove(qw);
    }
}
