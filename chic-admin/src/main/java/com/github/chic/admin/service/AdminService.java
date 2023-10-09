package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.admin.model.param.AdminAddParam;
import com.github.chic.admin.model.param.AdminDeleteParam;
import com.github.chic.admin.model.param.AdminUpdateParam;
import com.github.chic.admin.model.query.AdminQuery;
import com.github.chic.common.model.param.PageQuery;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Menu;
import com.github.chic.entity.Role;

import java.util.List;

public interface AdminService extends IService<Admin> {
    List<Admin> pageQuery(PageQuery page, AdminQuery query);

    void addByParam(AdminAddParam param);

    void updateByParam(AdminUpdateParam param);

    void deleteByParam(AdminDeleteParam param);

    Admin getByUsername(String username);

    List<Role> listRoleByAdminId(Long adminId);

    List<Menu> listMenuByAdminId(Long adminId);
}
