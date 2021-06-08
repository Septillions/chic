package com.github.chic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表
 */
@Data
@TableName("t_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 显示名称
     */
    private String title;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限代码
     */
    private String code;

    /**
     * URL路径
     */
    private String url;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 显示图标
     */
    private String icon;

    /**
     * 显示排序
     */
    private Integer sort;

    /**
     * 权限类型(1目录,2菜单,3按钮)
     */
    private Integer type;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
