package com.github.chic.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuListVO {
    @ApiModelProperty(value = "ID")
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

    @ApiModelProperty(value = "菜单说明")
    private String description;

    @ApiModelProperty(value = "类型(1菜单,2按钮)")
    private Integer type;

    @ApiModelProperty(value = "父级ID")
    private Long parentId;
}
