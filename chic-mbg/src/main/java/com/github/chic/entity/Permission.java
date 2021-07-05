package com.github.chic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField("title")
    private String title;

    /**
     * 权限名称
     */
    @TableField("name")
    private String name;

    /**
     * 权限代码
     */
    @TableField("code")
    private String code;

    /**
     * URL路径
     */
    @TableField("url")
    private String url;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 显示图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 显示排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 权限类型(1目录,2菜单,3按钮)
     */
    @TableField("type")
    private Integer type;

    /**
     * 父级ID
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
