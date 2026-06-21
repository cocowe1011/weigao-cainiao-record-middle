package com.middle.wcs.order.controller;

import com.middle.wcs.hander.ResponseResult;
import com.middle.wcs.order.entity.dto.RobotTaskRequest;
import com.middle.wcs.order.entity.dto.Values;
import com.middle.wcs.order.entity.po.QueueInfo;
import com.middle.wcs.order.service.QueueInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 对外开放接口 - AGV任务回调
 * AGV在任务执行过程中调用此接口通知WCS
 * 通过 extra.values.method（outbin/arrive）+ extra.values.slotName（分拣口编号GW01~GW13）定位队列
 *
 * 入参示例：
 * {
 *   "robotTaskCode": "MCS6b98b4e2...",
 *   "singleRobotCode": "28269",
 *   "currentSeq": -1,
 *   "extra": {
 *     "async": "0",
 *     "values": {
 *       "mapCode": "AA",
 *       "slotCategory": "SITE",
 *       "slotCode": "0495990AA0443480",
 *       "slotName": "GW03",
 *       "x": "...", "y": "...",
 *       "method": "arrive",
 *       "carrierCategory": "POD",
 *       "carrierType": "1",
 *       "carrierCode": "100013",
 *       "pileCount": 1,
 *       "taskTime": "2026-06-20 11:16:33.205",
 *       "amrCategory": "LMR",
 *       "amrType": "17",
 *       "amrCode": "28269",
 *       "carrierName": "100013",
 *       "carrierDir": "0.0",
 *       "layerNo": 1
 *     }
 *   }
 * }
 */
@Api(tags = "AGV对外开放接口")
@RestController
@RequestMapping("/api")
@Slf4j
public class ExternalInterfaceController {

    @Resource
    private QueueInfoService queueInfoService;

    @ApiOperation("AGV任务执行过程回馈接口")
    @PostMapping("/robot/reporter/task")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Boolean> task(@ApiParam(value = "入参", required = true)
                                        @RequestBody RobotTaskRequest dto) {
        log.info("AGV任务回调接口入参：{}", dto);

        if (dto.getExtra() == null || dto.getExtra().getValues() == null) {
            log.warn("AGV任务回调：extra或values为空，robotTaskCode={}", dto.getRobotTaskCode());
            return ResponseResult.success(true);
        }

        Values values = dto.getExtra().getValues();
        String method = values.getMethod();
        String slotName = values.getSlotName();

        if (method == null || slotName == null || slotName.isEmpty()) {
            log.warn("AGV任务回调：method或slotName为空，method={}, slotName={}, robotTaskCode={}",
                    method, slotName, dto.getRobotTaskCode());
            return ResponseResult.success(true);
        }

        // slotName格式：GW01~GW13，提取数字部分
        int slotNumber;
        try {
            slotNumber = Integer.parseInt(slotName.replace("GW", "").replace("gw", ""));
        } catch (NumberFormatException e) {
            log.warn("AGV任务回调：slotName格式错误，slotName={}, robotTaskCode={}",
                    slotName, dto.getRobotTaskCode());
            return ResponseResult.success(true);
        }

        // slotName = 分拣口编号(GW01~GW13)，queue_id = 分拣口编号 + 1
        long queueId = slotNumber + 1L;
        QueueInfo queueInfo = this.queueInfoService.getQueueInfoById(queueId);

        if (queueInfo == null) {
            log.warn("AGV任务回调：未找到队列，slotName={}, queueId={}, robotTaskCode={}",
                    slotName, queueId, dto.getRobotTaskCode());
            return ResponseResult.success(true);
        }

        log.info("AGV任务回调：method={}, slotName={}, queueId={}, queueName={}, 当前trayStatus={}, robotTaskCode={}",
                method, slotName, queueId, queueInfo.getQueueName(), queueInfo.getTrayStatus(), dto.getRobotTaskCode());

        QueueInfo queueInfoForUpdate = new QueueInfo();
        queueInfoForUpdate.setId(queueInfo.getId());

        switch (method) {
            case "outbin":
                // 满垛离开：AGV已取货离开分拣口，trayStatus 从 "0" 更新为 "1"
                if ("0".equals(queueInfo.getTrayStatus())) {
                    log.info("AGV任务回调-outbin：满垛离开，分拣口{}，trayStatus 0->1", slotName);
                    queueInfoForUpdate.setTrayStatus("1");
                    this.queueInfoService.update(queueInfoForUpdate);
                } else {
                    log.warn("AGV任务回调-outbin：分拣口{}，当前trayStatus={}，非'0'状态，跳过",
                            slotName, queueInfo.getTrayStatus());
                }
                break;

            case "arrive":
                // 空箱返回：AGV已送空托盘回来，trayStatus 从 "1" 更新为 "2"
                if ("1".equals(queueInfo.getTrayStatus())) {
                    log.info("AGV任务回调-arrive：空箱返回，分拣口{}，trayStatus 1->2", slotName);
                    queueInfoForUpdate.setTrayStatus("2");
                    this.queueInfoService.update(queueInfoForUpdate);
                } else {
                    log.warn("AGV任务回调-arrive：分拣口{}，当前trayStatus={}，非'1'状态，跳过",
                            slotName, queueInfo.getTrayStatus());
                }
                break;

            default:
                log.warn("AGV任务回调：未知method={}，分拣口{}, robotTaskCode={}",
                        method, slotName, dto.getRobotTaskCode());
                break;
        }

        return ResponseResult.success(true);
    }
}
