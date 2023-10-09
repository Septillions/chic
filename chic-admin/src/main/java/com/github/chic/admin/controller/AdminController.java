package com.github.chic.admin.controller;

import com.github.chic.admin.model.converter.BaseAdminConverter;
import com.github.chic.admin.model.param.AdminAddParam;
import com.github.chic.admin.model.param.AdminDeleteParam;
import com.github.chic.admin.model.param.AdminUpdateParam;
import com.github.chic.admin.model.query.AdminQuery;
import com.github.chic.admin.model.vo.AdminVO;
import com.github.chic.admin.service.AdminService;
import com.github.chic.common.model.api.ApiPage;
import com.github.chic.common.model.api.ApiResult;
import com.github.chic.common.model.param.PageQuery;
import com.github.chic.entity.Admin;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "Admin 管理员")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private BaseAdminConverter adminConverter;

    @ApiOperation("获取管理员列表")
    @GetMapping("/list")
    public ApiResult<ApiPage<AdminVO>> list(PageQuery page, AdminQuery query) {
        List<Admin> adminList = adminService.pageQuery(page, query);
        return ApiResult.page(adminConverter.entity2vo((Page<Admin>) adminList));
    }

    @ApiOperation("新增管理员")
    @PostMapping("/add")
    public ApiResult<Object> add(@RequestBody @Valid AdminAddParam param) {
        adminService.addByParam(param);
        return ApiResult.success();
    }

    @ApiOperation("更新管理员")
    @PutMapping("/update")
    public ApiResult<Object> update(@RequestBody @Valid AdminUpdateParam param) {
        adminService.updateByParam(param);
        return ApiResult.success();
    }

    @ApiOperation("删除管理员")
    @DeleteMapping("/delete")
    public ApiResult<Object> delete(@Valid AdminDeleteParam param) {
        adminService.deleteByParam(param);
        return ApiResult.success();
    }
}
