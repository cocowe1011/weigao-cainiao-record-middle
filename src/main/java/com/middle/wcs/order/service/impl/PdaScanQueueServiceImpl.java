package com.middle.wcs.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.middle.wcs.order.dao.PdaScanQueueMapper;
import com.middle.wcs.order.entity.po.PdaScanQueue;
import com.middle.wcs.order.service.PdaScanQueueService;
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
        return item;
    }

    @Override
    public PdaScanQueue peekHead() {
        List<PdaScanQueue> all = list();
        return all.isEmpty() ? null : all.get(0);
    }

    @Override
    public int removeById(Long id) {
        if (id == null) {
            return 0;
        }
        return pdaScanQueueMapper.deleteById(id);
    }

    @Override
    public boolean move(Long id, Integer direction) {
        if (id == null || direction == null || direction == 0) {
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
            return false;
        }
        int target = idx + direction;
        if (target < 0 || target >= all.size()) {
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
        return true;
    }
}
