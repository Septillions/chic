package com.github.chic.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import com.github.chic.admin.service.MonitorService;
import com.github.chic.common.model.api.ApiPage;
import com.github.chic.common.model.api.ApiResult;
import com.github.chic.common.model.dto.RedisJwtAdminDTO;
import com.github.chic.common.model.dto.RedisJwtUserDTO;
import com.github.chic.common.model.param.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Api(tags = "Monitor 监控")
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @Resource
    private MonitorService monitorService;

    @ApiOperation("获取在线管理员列表")
    @GetMapping("/findOnlineAdminList")
    public ApiResult<ApiPage<RedisJwtAdminDTO>> findOnlineAdminList(PageQuery page) {
        // 列表
        List<RedisJwtAdminDTO> redisJwtAdminDTOList = monitorService.listOnlineAdmin();
        // 比较器
        Comparator<RedisJwtAdminDTO> comparator = (o1, o2) -> {
            LocalDateTime t1 = o1.getLoginTime();
            LocalDateTime t2 = o2.getLoginTime();
            return t1.compareTo(t2);
        };
        // 分页
        List<RedisJwtAdminDTO> data = CollUtil.sortPageAll(page.getPageIndex() - 1, page.getPageSize(), comparator, redisJwtAdminDTOList);
        ApiPage<RedisJwtAdminDTO> apiPage = new ApiPage<>();
        apiPage.setPageIndex(page.getPageIndex());
        apiPage.setPageSize(page.getPageSize());
        apiPage.setPages(PageUtil.totalPage(redisJwtAdminDTOList.size(), page.getPageSize()));
        apiPage.setTotal((long) redisJwtAdminDTOList.size());
        apiPage.setItems(data);
        return ApiResult.success(apiPage);
    }

    @ApiOperation("获取在线用户列表")
    @GetMapping("/findOnlineUserList")
    public ApiResult<ApiPage<RedisJwtUserDTO>> findOnlineUserList(PageQuery page) {
        // 列表
        List<RedisJwtUserDTO> redisJwtUserDTOList = monitorService.listOnlineUser();
        // 比较器
        Comparator<RedisJwtUserDTO> comparator = (o1, o2) -> {
            LocalDateTime t1 = o1.getLoginTime();
            LocalDateTime t2 = o2.getLoginTime();
            return t1.compareTo(t2);
        };
        // 分页
        List<RedisJwtUserDTO> data = CollUtil.sortPageAll(page.getPageIndex() - 1, page.getPageSize(), comparator, redisJwtUserDTOList);
        ApiPage<RedisJwtUserDTO> apiPage = new ApiPage<>();
        apiPage.setPageIndex(page.getPageIndex());
        apiPage.setPageSize(page.getPageSize());
        apiPage.setPages(PageUtil.totalPage(redisJwtUserDTOList.size(), page.getPageSize()));
        apiPage.setTotal((long) redisJwtUserDTOList.size());
        apiPage.setItems(data);
        return ApiResult.success(apiPage);
    }
}
