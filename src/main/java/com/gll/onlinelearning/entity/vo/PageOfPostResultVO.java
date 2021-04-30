package com.gll.onlinelearning.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gll.onlinelearning.entity.Post;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false) //不调用父类进行比较，调用子类的方法进行比较
public class PageOfPostResultVO extends Post {
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})
    private SimpleUserVO uInfo;
    private List<AllLoveResultVO> likeList; // 点赞列表
    // 还有一个评论列表
    private List<AllCommentResultVO> commentList; //评论列表
}
