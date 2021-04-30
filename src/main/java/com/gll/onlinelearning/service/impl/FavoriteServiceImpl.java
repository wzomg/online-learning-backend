package com.gll.onlinelearning.service.impl;

import com.gll.onlinelearning.common.SystemConstant;
import com.gll.onlinelearning.entity.Favorite;
import com.gll.onlinelearning.entity.vo.PageOfFavoriteResultVO;
import com.gll.onlinelearning.mapper.FavoriteMapper;
import com.gll.onlinelearning.service.FavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author gll
 * @since 2021-04-04
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    @Override
    public boolean delFavoriteById(Integer id) {
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public List<PageOfFavoriteResultVO> pageOfFavoriteByUidOrTitle(Integer uid, Integer pageIndex, String searchContent) {
        return baseMapper.pageOfFavoriteByUidOrTitle(uid, (pageIndex - 1) * SystemConstant.PAGE_SIZE, SystemConstant.PAGE_SIZE, searchContent);
    }

    @Override
    public Map<String, Object> changeFavorite(Integer subjectId, Integer uid) {
        Map<String, Object> res = new HashMap<>();
        Integer code;
        String msg;
        boolean isOk;
        // 先查询一下数据库是否有此记录，有则更新，无则添加
        Favorite favorite = baseMapper.selectFavorite(subjectId, uid);
        if (favorite == null) { // 新增一条记录
            Favorite newFavorite = new Favorite();
            newFavorite.setSubjectId(subjectId);
            newFavorite.setUid(uid);
            isOk = baseMapper.insert(newFavorite) > 0;
            if (isOk) {
                code = 200;
                msg = "收藏成功！";
            } else {
                code = 3100;
                msg = "收藏失败!";
            }
        } else {
            if (favorite.getIsDeleted() == 1) { // 若已删除，则修改逻辑删除字段为0（未删除）
                isOk = baseMapper.updateFavorite(0, subjectId, uid, 1) > 0;
                if (isOk) {
                    code = 200;
                    msg = "收藏成功！";
                } else {
                    code = 3100;
                    msg = "收藏失败!";
                }
            } else { // 当前为点赞状态，需要修改为未点赞状态
                isOk = baseMapper.deleteById(favorite.getId()) > 0;
                if (isOk) {
                    code = 200;
                    msg = "取消收藏成功！";
                } else {
                    code = 3100;
                    msg = "取消收藏失败!";
                }
            }
        }
        res.put("code", code);
        res.put("msg", msg);
        return res;
    }

    @Override
    public int getCountOfFavoriteByUidOrTitle(Integer uid, String searchContent) {
        return baseMapper.countOfFavoriteByUidOrTitle(uid, searchContent);
    }
}
