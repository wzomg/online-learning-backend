package com.gll.onlinelearning.controller;


import com.gll.onlinelearning.common.HostHolder;
import com.gll.onlinelearning.service.LoveService;
import com.gll.onlinelearning.utils.SystemUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gll
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/love")
public class LoveController {

    @Resource
    private HostHolder hostHolder;

    @Resource
    private LoveService loveService;

    /**
     * 点赞 或 取消点赞，只需要传 帖子id，当前操作人id 直接从 hostHolder 中取即可
     */
    @GetMapping("/change")
    public String changeLove(Integer postId) {
        Integer uid = hostHolder.getUser().getId();
        Map<String, Object> res = loveService.changeLove(postId, uid);
        return SystemUtil.getJSONString((Integer) res.get("code"), (String) res.get("msg"), new HashMap<String, Object>() {{
            put("resLove", res.get("resLove"));
        }});
    }
}

