package com.github.chic.admin.controller;

import com.github.chic.admin.model.converter.BaseUserConverter;
import com.github.chic.admin.model.query.UserQuery;
import com.github.chic.admin.model.vo.UserVO;
import com.github.chic.admin.service.UserService;
import com.github.chic.common.model.api.ApiPage;
import com.github.chic.common.model.api.ApiResult;
import com.github.chic.common.model.param.PageQuery;
import com.github.chic.entity.User;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "User 用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private BaseUserConverter userConverter;

    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    public ApiResult<ApiPage<UserVO>> list(PageQuery page, UserQuery query) {
        List<User> userList = userService.pageQuery(page, query);
        return ApiResult.page(userConverter.entity2vo((Page<User>) userList));
    }
}