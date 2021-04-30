package com.gll.onlinelearning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.onlinelearning.entity.Love;

import java.util.Map;

/**
 * @author gll
 * @since 2021-04-05
 */
public interface LoveService extends IService<Love> {
    public Map<String, Object> changeLove(Integer postId, Integer uid);
}
