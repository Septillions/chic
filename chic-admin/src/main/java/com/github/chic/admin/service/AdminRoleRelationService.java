package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.entity.AdminRoleRelation;

/**
 * 管理员与角色关联表 服务类
 */
public interface AdminRoleRelationService extends IService<AdminRoleRelation> {

    void deleteByAdminId(Long adminId);
}
