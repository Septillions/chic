package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.admin.model.param.RoleAddParam;
import com.github.chic.admin.model.param.RoleDeleteParam;
import com.github.chic.admin.model.param.RoleUpdateParam;
import com.github.chic.admin.model.query.RoleQuery;
import com.github.chic.common.model.param.PageQuery;
import com.github.chic.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> pageQuery(PageQuery page, RoleQuery query);

    void addByParam(RoleAddParam param);

    void updateByParam(RoleUpdateParam param);

    void deleteByParam(RoleDeleteParam param);

    List<Role> listByAdminId(Long adminId);
}
