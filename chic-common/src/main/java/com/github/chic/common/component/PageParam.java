package com.github.chic.common.component;

import lombok.Data;

/**
 * 分页请求参数对象
 */
@Data
public class PageParam {
    private Integer pageIndex;
    private Integer pageSize;
}
