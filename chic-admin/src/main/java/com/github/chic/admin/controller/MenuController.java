package com.github.chic.admin.controller;

import com.github.chic.admin.model.param.MenuAddParam;
import com.github.chic.admin.model.param.MenuDeleteParam;
import com.github.chic.admin.model.param.MenuUpdateParam;
import com.github.chic.admin.model.query.MenuQuery;
import com.github.chic.admin.model.query.MenuRoleQuery;
import com.github.chic.admin.service.MenuService;
import com.github.chic.common.model.api.ApiPage;
import com.github.chic.common.model.api.ApiResult;
import com.github.chic.entity.Menu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "Menu 菜单权限")
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @ApiOperation("获取菜单权限列表")
    @GetMapping("/list")
    public ApiResult<ApiPage<Menu>> list(MenuQuery query) {
        List<Menu> menuList = menuService.listByQuery(query);
        return ApiResult.page(menuList);
    }

    @ApiOperation("新增菜单权限")
    @PostMapping("/add")
    public ApiResult<Object> add(@RequestBody @Valid MenuAddParam param) {
        menuService.addByParam(param);
        return ApiResult.success();
    }

    @ApiOperation("更新菜单权限")
    @PutMapping("/update")
    public ApiResult<Object> update(@RequestBody @Valid MenuUpdateParam param) {
        menuService.updateByParam(param);
        return ApiResult.success();
    }

    @ApiOperation("删除菜单权限")
    @DeleteMapping("/delete")
    public ApiResult<Object> delete(@Valid MenuDeleteParam param) {
        menuService.deleteByParam(param);
        return ApiResult.success();
    }

    @ApiOperation("获取角色的菜单权限列表")
    @GetMapping("/listByRole")
    public ApiResult<List<Menu>> listByRole(@Valid MenuRoleQuery query) {
        List<Menu> menuList = menuService.listByRole(query);
        return ApiResult.success(menuList);
    }
}
