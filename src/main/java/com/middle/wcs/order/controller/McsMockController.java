package com.middle.wcs.order.controller;

import com.middle.wcs.hander.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * MCS模拟接口（仅开发调试用）
 * 前端调用此接口，只返回成功，和真实MCS接口响应一致
 * 生产环境前端直接调MCS服务器，不走此接口
 */
@Api(tags = "MCS模拟接口（开发调试）")
@RestController
@RequestMapping("/mcs/api/v2/task")
@Slf4j
public class McsMockController {

    @ApiOperation("模拟MCS满垛发送接口")
    @PostMapping("/receiveSignal")
    public ResponseResult<Boolean> receiveSignal(@RequestBody Map<String, Object> payload) {
        log.info("MCS模拟接口收到请求：{}", payload);
        return ResponseResult.success(true);
    }
}
