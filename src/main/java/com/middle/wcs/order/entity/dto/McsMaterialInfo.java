package com.middle.wcs.order.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * MCS回调物料信息
 */
@Data
public class McsMaterialInfo {

    /**
     * 物料数据编码
     */
    private String materialDataCode;

    /**
     * 物料数据名称
     */
    private String materialDataName;

    /**
     * 载具信息列表
     */
    private List<McsCarrierInfo> carrierInfo;
}
