package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(description = "修改菜单权限参数")
@Data
public class MenuUpdateParam {
    @NotNull(message = "菜单ID不能为空")
    @ApiModelProperty(value = "菜单ID", required = true)
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "权限代码")
    private String code;

    @ApiModelProperty(value = "URL路径")
    private String url;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "显示图标")
    private String icon;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "菜单说明")
    private String description;

    @ApiModelProperty(value = "类型(1显示菜单,2操作权限)")
    private Integer type;

    @ApiModelProperty(value = "状态(0禁用,1启用)")
    private Integer status;

    @ApiModelProperty(value = "父级ID")
    private Long parentId;
}
