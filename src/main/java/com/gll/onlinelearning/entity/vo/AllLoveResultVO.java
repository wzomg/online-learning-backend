package com.gll.onlinelearning.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gll.onlinelearning.entity.Love;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true) //打印父类的字段值，转化json时自动去掉super
@EqualsAndHashCode(callSuper = false) //不调用父类进行比较，调用子类的方法进行比较
public class AllLoveResultVO extends Love {
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})
    private SimpleUserVO authorInfo; //发表人信息
}
