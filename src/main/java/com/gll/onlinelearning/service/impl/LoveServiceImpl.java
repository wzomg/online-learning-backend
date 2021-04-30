package com.gll.onlinelearning.service.impl;

import com.gll.onlinelearning.entity.Love;
import com.gll.onlinelearning.mapper.LoveMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.onlinelearning.service.LoveService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gll
 * @since 2021-04-05
 */
@Service
public class LoveServiceImpl extends ServiceImpl<LoveMapper, Love> implements LoveService {

    @Override
    public Map<String, Object> changeLove(Integer postId, Integer uid) {
        Map<String, Object> res = new HashMap<>();
        Integer code;
        String msg;
        boolean isOk;
        Love resLove = null;
        // 先查询一下数据库是否有此记录，有则更新，无则添加
        Love love = baseMapper.selectLove(postId, uid);
        if (love == null) { // 新增一条记录
            Love newLove = new Love();
            newLove.setPostId(postId);
            newLove.setUid(uid);
            isOk = baseMapper.insert(newLove) > 0;
            if (isOk) {
                code = 200;
                msg = "点赞成功！";
                resLove = baseMapper.selectById(newLove.getId());
            } else {
                code = 3000;
                msg = "点赞失败!";
            }
        } else {
            if (love.getIsDeleted() == 1) { // 若已删除，则修改逻辑删除字段为0（未删除）
                isOk = baseMapper.updateLove(0, postId, uid, 1) > 0;
                if (isOk) {
                    code = 200;
                    msg = "点赞成功！";
                    resLove = love;
                } else {
                    code = 3000;
                    msg = "点赞失败!";
                }
            } else { // 当前为点赞状态，需要修改为未点赞状态
                isOk = baseMapper.deleteById(love.getId()) > 0;
                if (isOk) {
                    code = 200;
                    msg = "取消点赞成功！";
                } else {
                    code = 3000;
                    msg = "取消点赞失败!";
                }
            }
        }
        res.put("code", code);
        res.put("msg", msg);
        res.put("resLove", resLove);
        return res;
    }
}
