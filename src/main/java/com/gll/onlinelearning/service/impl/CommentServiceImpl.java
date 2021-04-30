package com.gll.onlinelearning.service.impl;

import com.gll.onlinelearning.entity.Comment;
import com.gll.onlinelearning.mapper.CommentMapper;
import com.gll.onlinelearning.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author gll
 * @since 2021-04-05
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
