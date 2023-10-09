package com.github.chic.admin.controller;

import com.github.chic.admin.model.param.RoleAddParam;
import com.github.chic.admin.model.param.RoleDeleteParam;
import com.github.chic.admin.model.param.RoleUpdateParam;
import com.github.chic.admin.model.query.AdminRoleQuery;
import com.github.chic.admin.model.query.RoleQuery;
import com.github.chic.admin.service.RoleService;
import com.github.chic.common.model.api.ApiPage;
import com.github.chic.common.model.api.ApiResult;
import com.github.chic.common.model.param.PageQuery;
import com.github.chic.entity.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "Role 角色")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @ApiOperation("获取角色列表")
    @GetMapping("/list")
    public ApiResult<ApiPage<Role>> list(PageQuery page, RoleQuery query) {
        List<Role> roleList = roleService.pageQuery(page, query);
        return ApiResult.page(roleList);
    }

    @ApiOperation("新增角色")
    @PostMapping("/add")
    public ApiResult<Object> add(@RequestBody @Valid RoleAddParam param) {
        roleService.addByParam(param);
        return ApiResult.success();
    }

    @ApiOperation("更新角色")
    @PutMapping("/update")
    public ApiResult<Object> update(@RequestBody @Valid RoleUpdateParam param) {
        roleService.updateByParam(param);
        return ApiResult.success();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/delete")
    public ApiResult<Object> delete(@Valid RoleDeleteParam param) {
        roleService.deleteByParam(param);
        return ApiResult.success();
    }

    @ApiOperation("获取管理员的角色权限列表")
    @GetMapping("/listByAdmin")
    public ApiResult<List<Role>> listByAdmin(@Valid AdminRoleQuery query) {
        List<Role> roleList = roleService.listByAdminId(query.getAdminId());
        return ApiResult.success(roleList);
    }
}
