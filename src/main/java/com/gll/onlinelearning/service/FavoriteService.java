package com.gll.onlinelearning.service;

import com.gll.onlinelearning.entity.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.onlinelearning.entity.vo.PageOfFavoriteResultVO;

import java.util.List;
import java.util.Map;

/**
 * @author gll
 * @since 2021-04-04
 */
public interface FavoriteService extends IService<Favorite> {
    public boolean delFavoriteById(Integer id);

    public Map<String, Object> changeFavorite(Integer subjectId, Integer uid);

    public List<PageOfFavoriteResultVO> pageOfFavoriteByUidOrTitle(Integer uid, Integer pageIndex, String searchContent);


    public int getCountOfFavoriteByUidOrTitle(Integer uid, String searchContent);
}
