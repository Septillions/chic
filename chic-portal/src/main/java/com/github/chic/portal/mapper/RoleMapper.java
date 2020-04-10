package com.github.chic.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.chic.entity.Role;

import java.util.List;

/**
 * 角色表 Mapper 接口
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> selectRoleListByUserId(Integer userId);
}
