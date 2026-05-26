package com.middle.wcs.order.entity.po;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 大包订单信息 (OrderInfo)，字段对齐大包列表 CSV
 */
@Data
@TableName("order_info")
public class OrderInfo {
    /** 主键 */
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 作废标识：0未作废 1作废 */
    private String invalidFlag;

    /** WCS流转状态：1已上货 2分拣 3AGV运输中 4已送达WMS */
    private String trayStatus;

    /** WCS入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date insertTime;

    /** 完成时间（送达WMS） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;

    /** 大包号 */
    private String packageNo;

    /** 客户来源 */
    private String customerSource;

    /** 创建时间(UTC+08:00) */
    private String packageCreateTime;

    /** 来源仓 */
    private String sourceWarehouse;

    /** 计费重 */
    private String chargeWeight;

    /** 预计件数 */
    private String expectedQty;

    /** 实际件数 */
    private String actualQty;

    /** 渠道 */
    private String channel;

    /** 状态（如已装箱） */
    private String packageStatus;

    /** 目的国 */
    private String destinationCountry;

    /** 起运港 */
    private String departurePort;

    /** 目的港 */
    private String destinationPort;

    /** MBL提单号 */
    private String mblNo;

    /** 分单号 */
    private String subBillNo;

    /** 业务编号 */
    private String businessNo;

    /** 箱号 */
    private String containerNo;

    /** 封号 */
    private String sealNo;

    /** 装箱时间(UTC+08:00) */
    private String packingTime;

    /** 装箱人 */
    private String packer;

    /** 仓干交接时间(UTC+08:00) */
    private String handoverTime;

    /** 仓干交接人 */
    private String handoverPerson;

    /** 清关口岸 */
    private String customsPort;

    /** 提单收件人 */
    private String billReceiver;

    /** 批次号 */
    private String batchNo;

    /** 车牌号 */
    private String plateNo;
}
