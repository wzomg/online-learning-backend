package com.gll.onlinelearning.controller;

import com.gll.onlinelearning.common.HostHolder;
import com.gll.onlinelearning.entity.vo.PageOfFavoriteResultVO;
import com.gll.onlinelearning.service.FavoriteService;
import com.gll.onlinelearning.utils.SystemUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gll
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    @Resource
    private HostHolder hostHolder;

    /**
     * 添加或取消收藏，传个课程id即可，用户id从后台获取
     */
    @GetMapping("/change")
    public String addFavorite(Integer subjectId) {
        Map<String, Object> res = favoriteService.changeFavorite(subjectId, hostHolder.getUser().getId());
        return SystemUtil.getJSONString((Integer) res.get("code"), (String) res.get("msg"));
    }

    /**
     * 删除收藏
     */
    @DeleteMapping("/delete/{id}")
    public String delFavorite(@PathVariable("id") Integer id) {
        boolean isOk = favoriteService.delFavoriteById(id);
        int code = isOk ? 200 : 2300;
        String msg = isOk ? "删除成功！" : "删除失败！";
        return SystemUtil.getJSONString(code, msg);
    }

    /**
     * 分页查询出当前登录用户的收藏，当前登录的用户 uid 从 HostHolder 中取，可有模糊搜索共用一个接口
     */
    @GetMapping("/all")
    public String getPageOfFavorite(Integer pageIndex, String searchContent) {
        Integer uid = hostHolder.getUser().getId();
        List<PageOfFavoriteResultVO> voList = favoriteService.pageOfFavoriteByUidOrTitle(uid, pageIndex, searchContent);
        int total = favoriteService.getCountOfFavoriteByUidOrTitle(uid, searchContent);
        return SystemUtil.getJSONString(200, "", new HashMap<String, Object>() {{
            put("total", total);
            put("favoriteList", voList);
        }});
    }
}

