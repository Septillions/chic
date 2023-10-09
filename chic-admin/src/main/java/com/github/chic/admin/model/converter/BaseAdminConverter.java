package com.github.chic.admin.model.converter;

import com.github.chic.admin.model.vo.AdminVO;
import com.github.chic.common.component.base.BaseConverter;
import com.github.chic.entity.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BaseAdminConverter extends BaseConverter<Admin, AdminVO> {
}
