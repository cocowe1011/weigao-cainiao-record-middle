package com.middle.wcs.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.middle.wcs.order.dao.PdaScanQueueMapper;
import com.middle.wcs.order.entity.po.PdaScanQueue;
import com.middle.wcs.order.service.PdaScanQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * (PdaScanQueue)表服务实现类
 * 全部为行级操作：入队 INSERT、出队/删除 DELETE by id、排序 UPDATE 相邻两行 seq_no，
 * 不做整队列覆盖，天然规避 PDA端/WCS端 并发写丢数据。
 *
 * @author makejava
 * @since 2026-10-06 12:00:00
 */
@Slf4j
@Service("pdaScanQueueService")
public class PdaScanQueueServiceImpl implements PdaScanQueueService {

    @Resource
    private PdaScanQueueMapper pdaScanQueueMapper;

    @Override
    public List<PdaScanQueue> list() {
        QueryWrapper<PdaScanQueue> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("seq_no", "id");
        return pdaScanQueueMapper.selectList(wrapper);
    }

    @Override
    public PdaScanQueue push(PdaScanQueue item) {
        Integer max = pdaScanQueueMapper.selectMaxSeqNo();
        item.setId(null); // 强制走 MyBatis-Plus 雪花ID生成，忽略前端可能传入的id
        item.setSeqNo((max == null ? 0 : max) + 1);
        item.setScanTime(new Date());
        pdaScanQueueMapper.insert(item);
        log.info("PDA入队：大包号={}，id={}，seqNo={}，渠道={}，当前队列{}件",
                item.getPackageNo(), item.getId(), item.getSeqNo(), item.getChannel(),
                pdaScanQueueMapper.selectCount(new QueryWrapper<>()));
        return item;
    }

    @Override
    public PdaScanQueue peekHead() {
        List<PdaScanQueue> all = list();
        if (all.isEmpty()) {
            log.info("PDA查看队头：队列为空");
            return null;
        }
        PdaScanQueue head = all.get(0);
        log.info("PDA查看队头：大包号={}，id={}，seqNo={}，队列共{}件",
                head.getPackageNo(), head.getId(), head.getSeqNo(), all.size());
        return head;
    }

    @Override
    public int removeById(Long id) {
        if (id == null) {
            log.warn("PDA删除：id为空，未删除");
            return 0;
        }
        PdaScanQueue row = pdaScanQueueMapper.selectById(id);
        int rows = pdaScanQueueMapper.deleteById(id);
        if (row == null || rows == 0) {
            log.warn("PDA删除：未找到行，id={}，删除行数={}", id, rows);
            return rows;
        }
        log.info("PDA删除：大包号={}，id={}，seqNo={}，删除行数={}",
                row.getPackageNo(), id, row.getSeqNo(), rows);
        return rows;
    }

    @Override
    public boolean move(Long id, Integer direction) {
        if (id == null || direction == null || direction == 0) {
            log.warn("PDA调整顺序：参数无效，id={}，direction={}", id, direction);
            return false;
        }
        List<PdaScanQueue> all = list();
        int idx = -1;
        for (int i = 0; i < all.size(); i++) {
            if (id.equals(all.get(i).getId())) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            log.warn("PDA调整顺序：未找到行，id={}，direction={}", id, direction);
            return false;
        }
        int target = idx + direction;
        if (target < 0 || target >= all.size()) {
            log.info("PDA调整顺序：已到队列边界，大包号={}，direction={}", all.get(idx).getPackageNo(), direction);
            return false;
        }
        // 交换相邻两行的 seq_no（seq_no 由 push 的 max+1 保证唯一）
        PdaScanQueue a = all.get(idx);
        PdaScanQueue b = all.get(target);
        Integer seqA = a.getSeqNo();
        Integer seqB = b.getSeqNo();
        a.setSeqNo(seqB);
        b.setSeqNo(seqA);
        pdaScanQueueMapper.updateById(a);
        pdaScanQueueMapper.updateById(b);
        log.info("PDA调整顺序：大包号={} 与 {} 交换，direction={}",
                a.getPackageNo(), b.getPackageNo(), direction);
        return true;
    }
}
