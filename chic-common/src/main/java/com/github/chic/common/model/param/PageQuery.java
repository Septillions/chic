package com.github.chic.common.model.param;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("分页参数")
@Data
public class PageQuery {
    @ApiModelProperty(value = "查询页码", example = "1")
    private Integer pageIndex = 1;
    @ApiModelProperty(value = "每页数量", example = "20")
    private Integer pageSize = 20;
    @ApiModelProperty(value = "排序字段")
    private String sortField;
    @ApiModelProperty(value = "排序方式 (ASC,DESC)")
    private String sortType;

    public String getSort() {
        if (StrUtil.isNotBlank(this.sortField) && StrUtil.isNotBlank(this.sortType)) {
            String sortField = StrUtil.toUnderlineCase(this.sortField);
            String sortType = this.sortType.toUpperCase();
            return sortField + " " + sortType;
        }
        return null;
    }
}
