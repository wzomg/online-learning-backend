package com.gll.onlinelearning.mapper;

import com.gll.onlinelearning.entity.Love;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gll.onlinelearning.entity.vo.AllLoveResultVO;

import java.util.List;

/**
 * @author gll
 * @since 2021-04-05
 */
public interface LoveMapper extends BaseMapper<Love> {
    public Love selectLove(Integer postId, Integer uid);

    public int updateLove(Integer newStatus, Integer postId, Integer uid, Integer oldStatus);

    public int deleteLovesByPostId(Integer postId);

    public List<AllLoveResultVO> getLoveListByPostId(Integer postId);
}
