package com.gll.onlinelearning.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gll.onlinelearning.common.SystemConstant;
import com.gll.onlinelearning.entity.Post;
import com.gll.onlinelearning.entity.vo.PageOfPostResultVO;
import com.gll.onlinelearning.mapper.CommentMapper;
import com.gll.onlinelearning.mapper.LoveMapper;
import com.gll.onlinelearning.mapper.PostMapper;
import com.gll.onlinelearning.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gll
 * @since 2021-04-04
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Resource
    private LoveMapper loveMapper;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<PageOfPostResultVO> getPageOfPost(Integer pageIndex, String searchContent) {
        return baseMapper.getPageOfPostByContent((pageIndex - 1) * SystemConstant.PAGE_SIZE,
                SystemConstant.PAGE_SIZE, searchContent);
    }

    @Override
    public boolean updatePost(Post post) {
        UpdateWrapper<Post> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", post.getId());
        //第1个参数表示要修改的字段，第二个参数表示构造where子句
        return baseMapper.update(post, updateWrapper) > 0;
    }

    @Override
    public boolean delPostById(Integer postId) {
        // 删除该帖子的所有点赞（物理删除）
        int numOfOk = loveMapper.deleteLovesByPostId(postId) > 0 ? 1 : 0;
        // 删除该帖子的所有评论
        numOfOk += commentMapper.delCommentsByPostId(postId) > 0 ? 1 : 0;
        // 删除该帖子
        numOfOk += baseMapper.deleteById(postId) > 0 ? 1 : 0;
        return numOfOk > 0; // 可能没有点赞或者评论，所以删除的条数至少为1
    }

    @Override
    public int getCountOfPostByContent(String searchContent) {
        return baseMapper.countOfPostByContent(searchContent);
    }

    @Override
    public int updateReplynumOfPost(Integer deta, Integer postId) {
        return baseMapper.updateReplynumOfPost(deta, postId);
    }
}
