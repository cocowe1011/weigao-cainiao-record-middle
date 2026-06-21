package com.middle.wcs.order.entity.dto;

import lombok.Data;

/**
 * 自定义扩展字段
 */
@Data
public class Extra {
    /**
     * 是否异步：0-否
     */
    private String async;

    /**
     * 消息入参
     */
    private Values values;
}
