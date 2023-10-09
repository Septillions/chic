package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.admin.model.param.MenuAddParam;
import com.github.chic.admin.model.param.MenuDeleteParam;
import com.github.chic.admin.model.param.MenuUpdateParam;
import com.github.chic.admin.model.query.MenuQuery;
import com.github.chic.admin.model.query.MenuRoleQuery;
import com.github.chic.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> listByQuery(MenuQuery query);

    void addByParam(MenuAddParam param);

    void updateByParam(MenuUpdateParam param);

    void deleteByParam(MenuDeleteParam param);

    List<Menu> listByAdminId(Long adminId);

    List<Menu> listByRole(MenuRoleQuery query);
}
