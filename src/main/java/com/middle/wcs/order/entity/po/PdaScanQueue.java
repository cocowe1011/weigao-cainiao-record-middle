package com.middle.wcs.order.entity.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * PDA扫码队列明细 (PdaScanQueue)
 * 一个包裹一行；入队=INSERT、出队=DELETE队头行、删除=DELETE指定行、排序=交换seq_no，
 * 行级操作替代原 queue_info(id=17) 整队列JSON覆盖，避免 PDA端/WCS端 并发写互相覆盖。
 *
 * @author makejava
 * @since 2026-10-06 12:00:00
 */
@Data
@TableName("pda_scan_queue")
public class PdaScanQueue {

    /** 主键（MyBatis-Plus 雪花ID，非自增；序列化为字符串防止前端JS精度丢失） */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 大包号（条码） */
    private String packageNo;

    /** 渠道（菜鸟 laneCode） */
    private String channel;

    /** 包裹大小：large大包 / small小包 */
    private String packageSize;

    /** 计费重量（菜鸟 standardWeight），WCS出队落库order_info用 */
    private String chargeWeight;

    /** 包装重量 */
    private String packingWeight;

    /** 预期小包数 */
    private String expectedQty;

    /** 队列顺序号，越小越靠前，队头=最小 */
    private Integer seqNo;

    /** 扫码入队时间 */
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss", timezone = "GMT+8")
    private Date scanTime;
}
