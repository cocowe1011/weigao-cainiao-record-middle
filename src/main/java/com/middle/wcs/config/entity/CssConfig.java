package com.middle.wcs.config.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @classDesc: 实体类:(CssConfig)
 * @author: makejava
 * @date: 2023-07-04 15:31:49
 * @copyright 作者
 */
@Data
@TableName("css_config")
public class CssConfig {
    /**
    * 主键
    */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long configId;

    /**
    * 连接plcip地址
    */    
    private String plcIp;
                
    /**
    * 连接plc端口
    */    
    private String plcPort;
                
    /**
    * L11长度
    */    
    private String oneOneLength;
                
    /**
    * l2长度
    */    
    private String twoLength;
                
    /**
    * 判断禁止上货点位
    */    
    private String judgeLoadPoint;

    /**
     * J
     */
    private BigDecimal pointjLength;

    /**
     * K
     */
    private BigDecimal pointkLength;

    /**
     * L
     */
    private BigDecimal pointlLength;

    /**
     * V1比例系数
     */
    private BigDecimal speedOne;

    /**
     * V2比例系数
     */
    private BigDecimal speedTwo;

    /**
     * X1长度
     */
    private BigDecimal lengthOne;

    /**
     * X2长度
     */
    private BigDecimal lengthTwo;

    /**
     * 延迟时间
     */
    private Integer newDelayPointTime;


    /**
     * 中英文配置
     */
    private String languageSet;

    /**
     * 触发分拣机光电后应到达的误差（秒，±）
     */
    private BigDecimal arrivalToleranceSec;

    /**
     * 清理未进入分拣口包裹的时间（秒）：已发转向命令后仍未进分拣口则删除
     */
    private BigDecimal notEnteredTimeoutSec;

    /**
     * 清理超时未发送分拣命令的包裹的时间（秒）：超过应到达时刻后再等待该时长仍未发命令则删除
     */
    private BigDecimal cmdNotSentTimeoutSec;

    /**
     * 大包分拣口容量
     */
    private Integer largePortCapacity;

    /**
     * 小包分拣口容量
     */
    private Integer smallPortCapacity;

    /**
     * 五面扫进队到分拣机1的行进时间（秒）
     */
    private BigDecimal sorter1TravelSec;

    /**
     * 五面扫进队到分拣机2的行进时间（秒）
     */
    private BigDecimal sorter2TravelSec;

    /**
     * 五面扫进队到分拣机3的行进时间（秒）
     */
    private BigDecimal sorter3TravelSec;

    /**
     * 五面扫进队到分拣机4的行进时间（秒）
     */
    private BigDecimal sorter4TravelSec;

    /**
     * 五面扫进队到分拣机5的行进时间（秒）
     */
    private BigDecimal sorter5TravelSec;

    /**
     * 五面扫进队到分拣机6的行进时间（秒）
     */
    private BigDecimal sorter6TravelSec;

    /**
     * 五面扫进队到X光机光电的行进时间（秒）
     */
    private BigDecimal xrayTravelSec;

}
