package com.github.chic.common.component;

import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页返回格式对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {
    private Integer code;
    private String msg;
    private Object data;

    public static <T> PageResult success(Page<T> page) {
        Map<Object, Object> resultMap = new HashMap<>(5);
        resultMap.put("pageIndex", page.getPageNum());
        resultMap.put("pageSize", page.getPageSize());
        resultMap.put("totle", page.getTotal());
        resultMap.put("items", page.getResult());
        PageResult pageResult = new PageResult();
        pageResult.setCode(ResultCode.SUCCESS.getCode());
        pageResult.setMsg(ResultCode.SUCCESS.getMsg());
        pageResult.setData(resultMap);
        return pageResult;
    }
}
