package com.github.chic.admin.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(description = "管理员角色查询")
@Data
public class AdminRoleQuery {
    @NotNull(message = "管理员ID不能为空")
    @ApiModelProperty(value = "管理员ID")
    private Long adminId;
}
