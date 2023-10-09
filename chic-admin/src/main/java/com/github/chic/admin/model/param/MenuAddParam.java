package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "新增菜单权限参数")
@Data
public class MenuAddParam {
    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称", required = true)
    private String name;

    @ApiModelProperty(value = "权限代码")
    private String code;

    @ApiModelProperty(value = "URL路径")
    private String url;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "显示图标")
    private String icon;

    @NotNull(message = "显示排序不能为空")
    @ApiModelProperty(value = "显示排序", required = true)
    private Integer sort;

    @ApiModelProperty(value = "菜单说明")
    private String description;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型(1显示菜单,2操作权限)", required = true)
    private Integer type;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态(0禁用,1启用)", required = true)
    private Integer status;

    @NotNull(message = "父级ID不能为空")
    @ApiModelProperty(value = "父级ID", required = true)
    private Long parentId;
}
