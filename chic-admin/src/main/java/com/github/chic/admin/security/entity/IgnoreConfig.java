package com.github.chic.admin.security.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证忽略配置类
 */
@Data
public class IgnoreConfig {
    /**
     * 需要忽略的 URL 格式，不考虑请求方法
     */
    private List<String> pattern = new ArrayList<>();
    /**
     * 需要忽略的 GET 请求
     */
    private List<String> get = new ArrayList<>();
    /**
     * 需要忽略的 POST 请求
     */
    private List<String> post = new ArrayList<>();
    /**
     * 需要忽略的 DELETE 请求
     */
    private List<String> delete = new ArrayList<>();
    /**
     * 需要忽略的 PUT 请求
     */
    private List<String> put = new ArrayList<>();
    /**
     * 需要忽略的 HEAD 请求
     */
    private List<String> head = new ArrayList<>();
    /**
     * 需要忽略的 PATCH 请求
     */
    private List<String> patch = new ArrayList<>();
    /**
     * 需要忽略的 OPTIONS 请求
     */
    private List<String> options = new ArrayList<>();
    /**
     * 需要忽略的 TRACE 请求
     */
    private List<String> trace = new ArrayList<>();
}
