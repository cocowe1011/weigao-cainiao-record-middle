package com.middle.wcs.order.entity.dto;

import lombok.Data;

/**
 * 消息入参
 * 机器人任务请求接口入参
 */
@Data
public class Values {
    /**
     * 地图编号
     */
    private String mapCode;

    /**
     * 存储类型，枚举值：BIN-仓位，SITE-站点
     */
    private String slotCategory;

    /**
     * 当前站点编号
     */
    private String slotCode;

    /**
     * 分拣口编号（站点别名）
     * 格式：GW01~GW13
     * 1.走出储位(outbin)：起点
     * 2.任务完成(arrive)：目标点
     */
    private String slotName;

    /**
     * 机器人当前位置 x 坐标
     */
    private String x;

    /**
     * 机器人当前位置 y 坐标
     */
    private String y;

    /**
     * 任务执行过程中消息上报的方法名
     * outbin : 走出储位（满垛离开）
     * arrive : 到达站点（空箱返回）
     */
    private String method;

    /**
     * 载具种类
     */
    private String carrierCategory;

    /**
     * 载具类型
     */
    private String carrierType;

    /**
     * 载具编号
     */
    private String carrierCode;

    /**
     * 堆叠数量
     */
    private Integer pileCount;

    /**
     * 任务时间
     */
    private String taskTime;

    /**
     * 机器人种类
     */
    private String amrCategory;

    /**
     * 机器人类型
     */
    private String amrType;

    /**
     * 机器人编码
     */
    private String amrCode;

    /**
     * 载具名称
     */
    private String carrierName;

    /**
     * 载具角度
     */
    private String carrierDir;

    /**
     * 层号
     */
    private Integer layerNo;
}
