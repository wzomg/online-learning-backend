package com.gll.onlinelearning.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.ArrayList;
import java.util.Date;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author gll
 * @since 2021-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Post对象", description = "")
@TableName(autoResultMap = true)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "帖子id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "帖子内容")
    private String content;

    @ApiModelProperty(value = "评论数")
    private Integer replynum;

    /**
     * 注意！！ 使用高级特性必须开启映射注解
     * 支持 MVC JSON 解析
     * 支持 MySQL JSON 解析
     *
     * @TableName(autoResultMap = true)
     */
    @ApiModelProperty(value = "上传的图片列表")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> pictures = new ArrayList<>(); // 给定一个默认值

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
