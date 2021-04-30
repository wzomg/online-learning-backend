package com.gll.onlinelearning.mapper;

import com.gll.onlinelearning.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gll.onlinelearning.entity.vo.AllCommentResultVO;

import java.util.List;


/**
 * @author gll
 * @since 2021-04-05
 */
public interface CommentMapper extends BaseMapper<Comment> {
    int delCommentsByPostId(Integer postId);

    List<AllCommentResultVO> getAllCommentByPostId(Integer postId);
}
