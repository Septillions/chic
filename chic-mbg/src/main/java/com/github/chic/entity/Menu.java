package com.github.chic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单表
 */
@Data
@TableName("t_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名称
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
     * 菜单说明
     */
    @TableField("description")
    private String description;

    /**
     * 类型(1显示菜单,2操作权限)
     */
    @TableField("type")
    private Integer type;

    /**
     * 状态(0禁用,1启用)
     */
    @TableField("status")
    private Integer status;

    /**
     * 父级ID
     */
    @TableField("parent_id")
    private Long parentId;

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
