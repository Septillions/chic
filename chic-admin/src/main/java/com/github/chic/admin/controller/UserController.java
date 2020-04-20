package com.github.chic.admin.controller;

import com.github.chic.admin.model.converter.UserConverter;
import com.github.chic.admin.model.param.UserParam;
import com.github.chic.admin.model.vo.UserVO;
import com.github.chic.admin.service.UserService;
import com.github.chic.common.entity.api.ApiPage;
import com.github.chic.common.entity.api.ApiResult;
import com.github.chic.common.entity.param.PageParam;
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
    private UserConverter userConverter;

    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    public ApiResult<ApiPage<UserVO>> list(PageParam pageParam, UserParam userParam) {
        List<User> userList = userService.listByParam(pageParam, userParam);
        return ApiResult.success(userConverter.entity2vo((Page<User>) userList));
    }
}