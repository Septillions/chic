package com.github.chic.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.chic.admin.model.query.MenuRoleQuery;
import com.github.chic.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表 Mapper 接口
 */
public interface MenuDao extends BaseMapper<Menu> {
    List<Menu> listByAdminId(Long adminId);

    List<Menu> listByRole(@Param("query") MenuRoleQuery query);
}
