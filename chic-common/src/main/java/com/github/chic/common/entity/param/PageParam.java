package com.github.chic.common.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("分页参数")
@Data
public class PageParam {
    @ApiModelProperty(value = "查询页码")
    private Integer pageIndex;
    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;
}
