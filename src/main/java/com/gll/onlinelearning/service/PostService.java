package com.gll.onlinelearning.service;

import com.gll.onlinelearning.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.onlinelearning.entity.vo.PageOfPostResultVO;

import java.util.List;

/**
 * @author gll
 * @since 2021-04-04
 */
public interface PostService extends IService<Post> {
    public List<PageOfPostResultVO> getPageOfPost(Integer pageIndex, String searchContent);

    public boolean updatePost(Post post);

    public boolean delPostById(Integer postId);

    public int getCountOfPostByContent(String searchContent);

    public int updateReplynumOfPost(Integer deta, Integer postId);
}
