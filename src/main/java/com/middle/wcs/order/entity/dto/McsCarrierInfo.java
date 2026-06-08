package com.middle.wcs.order.entity.dto;

import lombok.Data;

/**
 * MCS回调载具信息
 */
@Data
public class McsCarrierInfo {

    /**
     * 载具编码
     */
    private String carrierCode;

    /**
     * 载具类型名称
     */
    private String carrierTypeName;

    /**
     * 层数
     */
    private Integer layer;

    /**
     * 载具种类
     */
    private Integer carrierCategory;

    /**
     * 载具种类名称
     */
    private String carrierCategoryName;

    /**
     * 储位类型
     */
    private Integer slotType;

    /**
     * 储位类型名称
     */
    private String slotTypeName;

    /**
     * 运输方式
     */
    private Integer transportMode;

    /**
     * AMM编码
     */
    private String ammCode;

    /**
     * 自定义数据
     */
    private String data;
}
