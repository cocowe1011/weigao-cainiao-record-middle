package com.middle.wcs.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.middle.wcs.order.entity.po.PdaScanQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * (PdaScanQueue)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-06 12:00:00
 */
@Mapper
public interface PdaScanQueueMapper extends BaseMapper<PdaScanQueue> {

    /**
     * 查询当前队列最大顺序号（无数据返回0），用于入队时生成 seq_no = max + 1
     */
    @Select("SELECT ISNULL(MAX(seq_no), 0) FROM pda_scan_queue")
    Integer selectMaxSeqNo();
}
