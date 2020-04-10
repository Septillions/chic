package com.github.chic.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.github.chic.admin.model.dto.RedisJwtDTO;
import com.github.chic.admin.service.MonitorService;
import com.github.chic.common.component.JsonResult;
import com.github.chic.common.component.PageResult;
import com.github.chic.common.component.PageParam;
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
@RequestMapping("/admin/monitor")
public class MonitorController {
    @Resource
    private MonitorService monitorService;

    @ApiOperation("获取在线管理员列表")
    @GetMapping("/findOnlineAdminList")
    public JsonResult<PageResult<RedisJwtDTO>> findOnlineAdminList(PageParam pageParam) {
        // 列表
        List<RedisJwtDTO> redisJwtDTOList = monitorService.listOnlineAdmin();
        // 比较器
        Comparator<RedisJwtDTO> comparator = (o1, o2) -> {
            LocalDateTime t1 = o1.getLoginTime();
            LocalDateTime t2 = o2.getLoginTime();
            return t1.compareTo(t2);
        };
        // 分页
        List<RedisJwtDTO> data = CollUtil.sortPageAll(pageParam.getPageIndex() - 1, pageParam.getPageSize(), comparator, redisJwtDTOList);
        PageResult<RedisJwtDTO> pageResult = new PageResult<>();
        pageResult.setPageIndex(pageParam.getPageIndex());
        pageResult.setPageSize(pageParam.getPageSize());
        pageResult.setTotle((long) redisJwtDTOList.size());
        pageResult.setItems(data);
        return JsonResult.success(pageResult);
    }
}
