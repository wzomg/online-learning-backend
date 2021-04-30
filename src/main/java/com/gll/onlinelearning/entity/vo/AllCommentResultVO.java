package com.gll.onlinelearning.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gll.onlinelearning.entity.Comment;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AllCommentResultVO extends Comment {
    //局部关闭循环引用
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})
    private SimpleUserVO authorInfo; //发表人信息
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})
    private SimpleUserVO targetInfo; //被人回复的人信息
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})
    private List<AllCommentResultVO> replyList; //回复列表
}
