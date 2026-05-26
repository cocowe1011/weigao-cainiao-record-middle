package com.middle.wcs.order.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 订单分页查询入参（与 {@link com.middle.wcs.order.entity.po.OrderInfo} 字段对应）
 */
@Data
public class OrderInfoPageDTO {

    @NotNull(message = "起始页数不能为空")
    private Integer pageNum;

    @NotNull(message = "每页大小不能为空")
    private Integer pageSize;

    /** 大包号（模糊） */
    private String packageNo;

    /** 客户来源（模糊） */
    private String customerSource;

    /** 批次号（模糊） */
    private String batchNo;

    /** 业务编号（模糊） */
    private String businessNo;

    /** WCS流转状态：1已上货 2分拣 3AGV运输中 4已送达WMS */
    private String trayStatus;

    /** 作废标识：0未作废 1作废 */
    private String invalidFlag;

    /** 目的国（模糊） */
    private String destinationCountry;

    /** 来源仓 */
    private String sourceWarehouse;

    /** 上货日期（对应 insert_time，格式 yyyy-MM-dd） */
    private String productionDate;

    /** 渠道（模糊） */
    private String channel;

    /** 状态（如已装箱） */
    private String packageStatus;
}
