package com.gll.onlinelearning.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gll
 * @since 2021-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父级评论id")
    private Integer parentId;

    @ApiModelProperty(value = "评论者id")
    private Integer uid;

    @ApiModelProperty(value = "帖子id")
    private Integer postId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    //评论的层级，比如对朋友圈的直接评论level为0，回复直接评论level为1，回复对直接评论的评论为2
    @ApiModelProperty(value = "评论层级（0，1，2）")
    private Integer level;

    /**
     * 用户A发表评论P2评论了朋友圈的直接评论P1，targetId 就是直接评论P1的用户id
     * 用户B发表评论P3评论了用户A的评论P2， targetId 就是用户A的id
     * 注意：P2评论和P3评论的parentId都是P1的Id
     */
    @ApiModelProperty(value = "目标人id")
    private Integer targetId;

    @ApiModelProperty(value = "评论时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
