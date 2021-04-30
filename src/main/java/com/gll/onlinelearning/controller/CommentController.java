package com.gll.onlinelearning.controller;


import com.gll.onlinelearning.entity.Comment;
import com.gll.onlinelearning.service.CommentService;
import com.gll.onlinelearning.service.PostService;
import com.gll.onlinelearning.utils.SystemUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author gll
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private PostService postService;

    /**
     * 添加评论
     */
    @PostMapping("/add")
    public String addComment(@RequestBody Comment comment) {
        boolean isOk = commentService.save(comment);
        //重新查询一下插入的评论信息
        Comment one = isOk ? commentService.getById(comment.getId()) : null;
        if (isOk) {
            //查询成功才往 post 表更新 replynum 字段
            postService.updateReplynumOfPost(1, comment.getPostId());
            return SystemUtil.getJSONString(200, "评论成功！", new HashMap<String, Object>() {{
                put("comment", one);
            }});
        }
        return SystemUtil.getJSONString(4000, "评论失败！");
    }

    /**
     * todo：暂时不做这个功能
     * 传评论 id 和 评论层级 level
     * 删除评论，传一个参数，评论id，还要对其子评论进行递归删除
     * 分情况讨论
     *  ①若 level 为0，则直接删除 id 为 id的那条评论 和 parentId 为 id 的所有记录
     *  ②若 level 为1，则拿出那条记录的 parentId，然后查出 parentId+(level=2)+（targetId=待删除level=1的评论的那个uid）
     *      另外加上直接删除该条评论
     *  ③若 level 为2，则直接删除 id 为 id的那条评论
     *  ④减去该帖子被删除的回复数
     */
    @PostMapping("/del/{id}")
    public String delComment(@PathVariable("id") Integer id) {
        boolean isOk = commentService.removeById(id);
        int code = isOk ? 200 : 2300;
        String msg = isOk ? "删除成功！" : "删除失败！";
        return SystemUtil.getJSONString(code, msg);
    }
}

