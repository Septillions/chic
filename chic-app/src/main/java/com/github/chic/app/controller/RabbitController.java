package com.github.chic.app.controller;

import com.github.chic.common.model.api.ApiResult;
import com.github.chic.app.component.mq.RabbitProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "RabbitMQ 消息队列")
@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    @Resource
    private RabbitProducer rabbitProducer;

    @ApiOperation("发送延时消息")
    @PostMapping("/sendDelayMsg")
    public ApiResult<Object> sendDelayMsg() {
        rabbitProducer.sendDelayMsg(new Date(), 5 * 1000);
        return ApiResult.success();
    }
}
