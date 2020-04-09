package com.github.chic.common.component;

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
public class PageResult<T> {
    private Integer pageIndex;
    private Integer pageSize;
    private Long totle;
    private List<T> items;
}
