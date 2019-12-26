package com.github.chic.portal.controller;

import com.github.chic.common.component.JsonResult;
import com.github.chic.portal.model.dto.LoginParam;
import com.github.chic.portal.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 登陆
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginParam loginParam) {
        String token = userService.login(loginParam);
        Map<String, Object> resultMap = new HashMap<>(5);
        resultMap.put("token", token);
        return JsonResult.success(resultMap);
    }
}
