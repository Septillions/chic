package com.github.chic.common.model.api;

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
    private Integer pageIndex;
    private Integer pageSize;
    private Integer pages;
    private Long total;
    private List<T> items;
}
