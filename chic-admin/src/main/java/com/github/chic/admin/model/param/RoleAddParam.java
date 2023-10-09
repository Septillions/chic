package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "新增角色参数")
@Data
public class RoleAddParam {
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;

    @NotBlank(message = "角色代码不能为空")
    @ApiModelProperty(value = "角色代码", required = true)
    private String code;

    @NotNull(message = "显示排序不能为空")
    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "角色说明")
    private String description;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态(0禁用,1启用)", required = true)
    private Integer status;

    @ApiModelProperty(value = "角色菜单ID列表")
    private List<Long> menuIdList;
}
