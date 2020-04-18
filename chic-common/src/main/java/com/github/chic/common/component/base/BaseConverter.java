package com.github.chic.common.component.base;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct 基础转换器
 *
 * @param <E> Entity
 * @param <V> VO
 */
public abstract class BaseConverter<E, V> {
    public abstract V entity2vo(E entity);

    public abstract List<V> entity2vo(List<E> entityList);

    public abstract Page<V> entity2vo(Page<E> entityPage);

    @AfterMapping
    public void setPage(Page<?> source, @MappingTarget Page<?> target) {
        BeanUtil.copyProperties(source, target);
    }
}
