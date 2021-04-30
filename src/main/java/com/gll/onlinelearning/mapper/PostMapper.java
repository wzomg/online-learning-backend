package com.gll.onlinelearning.mapper;

import com.gll.onlinelearning.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gll.onlinelearning.entity.vo.PageOfPostResultVO;

import java.util.List;

/**
 * @author gll
 * @since 2021-04-04
 */
public interface PostMapper extends BaseMapper<Post> {
    List<PageOfPostResultVO> getPageOfPostByContent(Integer offset, Integer limit, String searchContent);

    int countOfPostByContent(String searchContent);

    int updateReplynumOfPost(Integer deta, Integer postId);
}
