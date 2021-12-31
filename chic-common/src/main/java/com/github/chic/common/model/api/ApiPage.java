package com.github.chic.common.model.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页返回格式对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiPage<T> {
    @ApiModelProperty(value = "当前页码", position = 1)
    private Integer pageIndex;
    @ApiModelProperty(value = "每页条数", position = 2)
    private Integer pageSize;
    @ApiModelProperty(value = "总页数", position = 3)
    private Integer pages;
    @ApiModelProperty(value = "总条数", position = 4)
    private Long total;
    @ApiModelProperty(value = "数据列表", position = 5)
    private List<T> items;
}
