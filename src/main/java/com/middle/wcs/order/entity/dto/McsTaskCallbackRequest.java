package com.middle.wcs.order.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * MCS任务反馈通知第三方回调请求体
 * 对应接口：POST /api/robot/reporter/task
 */
@Data
public class McsTaskCallbackRequest {

    /**
     * 信号ID
     */
    private String signalId;

    /**
     * 方法名：start/outbin/end/cancel
     */
    private String method;

    /**
     * 起点储位编码
     */
    private Integer startSlotCode;

    /**
     * 终点储位编码
     */
    private Integer endSlotCode;

    /**
     * 起点机台编码
     */
    private String startMachineCode;

    /**
     * 终点机台编码
     */
    private String endMachineCode;

    /**
     * 起点工作站编码
     */
    private String startWorkstationCode;

    /**
     * 终点工作站编码
     */
    private String endWorkstationCode;

    /**
     * 起点区域编码
     */
    private String startAreaCode;

    /**
     * 终点区域编码
     */
    private String endAreaCode;

    /**
     * 物料信息列表
     */
    private List<McsMaterialInfo> materialInfo;

    /**
     * AMR编码（机器人编码）
     */
    private String amrCode;

    /**
     * 自定义数据字段
     */
    private String data;
}
