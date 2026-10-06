package com.middle.wcs.order.service;

import com.middle.wcs.order.entity.po.PdaScanQueue;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * (PdaScanQueue)表服务接口
 *
 * @author makejava
 * @since 2026-10-06 12:00:00
 */
@Validated
public interface PdaScanQueueService {

    /** 按 seq_no 升序查询队列（队头在前） */
    List<PdaScanQueue> list();

    /** 入队：追加到队尾，自动生成 seq_no 与 scan_time */
    PdaScanQueue push(PdaScanQueue item);

    /** 查看队头（不删除），队列为空返回 null */
    PdaScanQueue peekHead();

    /** 按主键删除指定行（出队/人工删除），返回影响行数 */
    int removeById(Long id);

    /** 上移/下移：direction=-1 上移，1 下移；与相邻行交换 seq_no */
    boolean move(Long id, Integer direction);
}
