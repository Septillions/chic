package com.github.chic.admin.model.converter;

import com.github.chic.admin.model.vo.UserVO;
import com.github.chic.common.component.base.BaseConverter;
import com.github.chic.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BaseUserConverter extends BaseConverter<User, UserVO> {
}
