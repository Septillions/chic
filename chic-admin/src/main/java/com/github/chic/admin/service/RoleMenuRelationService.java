package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.entity.RoleMenuRelation;

/**
 * 角色与菜单关联表 服务类
 */
public interface RoleMenuRelationService extends IService<RoleMenuRelation> {

    void deleteByRoleId(Long roleId);
}
