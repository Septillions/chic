package com.github.chic.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.chic.entity.Permission;

import java.util.List;

/**
 * 权限表 Mapper 接口
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectPermissionListByUserId(Integer userId);
}
