package com.middle.wcs.order.controller;

import com.middle.wcs.hander.ResponseResult;
import com.middle.wcs.order.entity.po.PdaScanQueue;
import com.middle.wcs.order.service.PdaScanQueueService;
import javax.annotation.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (PdaScanQueue)PDA扫码队列控制器
 * 一个包裹一行，行级增删改，替代原 queue_info(id=17) 整队列JSON覆盖。
 *
 * @author makejava
 * @since 2026-10-06 12:00:00
 */

@RestController
@Api(tags = "PDA扫码队列接口")
@RequestMapping("/pda_queue")
public class PdaScanQueueController {

    @Resource
    private PdaScanQueueService pdaScanQueueService;

    @ApiOperation("查询PDA扫码队列（按顺序，队头在前）")
    @GetMapping("/list")
    public ResponseResult<List<PdaScanQueue>> list() {
        return ResponseResult.success(this.pdaScanQueueService.list());
    }

    @ApiOperation("入队（追加到队尾）")
    @PostMapping("/push")
    public ResponseResult<PdaScanQueue> push(@ApiParam(value = "包裹信息", required = true) @RequestBody PdaScanQueue item) {
        return ResponseResult.success(this.pdaScanQueueService.push(item));
    }

    @ApiOperation("查看队头（不删除）")
    @GetMapping("/peekHead")
    public ResponseResult<PdaScanQueue> peekHead() {
        return ResponseResult.success(this.pdaScanQueueService.peekHead());
    }

    @ApiOperation("按主键删除指定行（出队/人工删除）")
    @PostMapping("/removeById")
    public ResponseResult<Integer> removeById(@ApiParam(value = "主键ID", required = true) @RequestParam Long id) {
        return ResponseResult.success(this.pdaScanQueueService.removeById(id));
    }

    @ApiOperation("上移/下移（direction=-1上移，1下移）")
    @PostMapping("/move")
    public ResponseResult<Boolean> move(@ApiParam(value = "主键ID", required = true) @RequestParam Long id, @ApiParam(value = "方向：-1上移，1下移", required = true) @RequestParam Integer direction) {
        return ResponseResult.success(this.pdaScanQueueService.move(id, direction));
    }
}
