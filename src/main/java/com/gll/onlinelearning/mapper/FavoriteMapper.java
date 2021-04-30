package com.gll.onlinelearning.mapper;

import com.gll.onlinelearning.entity.Favorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gll.onlinelearning.entity.vo.PageOfFavoriteResultVO;

import java.util.List;

/**
 * @author gll
 * @since 2021-04-04
 */
public interface FavoriteMapper extends BaseMapper<Favorite> {
    List<PageOfFavoriteResultVO> pageOfFavoriteByUidOrTitle(Integer uid, Integer offset, Integer limit, String searchContent);

    int countOfFavoriteByUidOrTitle(Integer uid, String searchContent);

    Favorite selectFavorite(Integer subjectId, Integer uid);

    int updateFavorite(Integer newStatus, Integer subjectId, Integer uid, Integer oldStatus);
}
