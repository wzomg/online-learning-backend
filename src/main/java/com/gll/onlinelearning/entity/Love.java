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
 * 注意：数据库不能命名为 like，所以定义为 love
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Love对象", description = "")
public class Love implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "点赞id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "帖子id")
    private Integer postId;

    @ApiModelProperty(value = "点赞人id")
    private Integer uid;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic //逻辑删除
    // @TableField(select = false) // 查询时不显示此字段
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
