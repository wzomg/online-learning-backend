package com.gll.onlinelearning.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

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
@ApiModel(value = "Note对象", description = "")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "笔记id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "下载链接")
    private String downloadUrl;

    @ApiModelProperty(value = "上传时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
