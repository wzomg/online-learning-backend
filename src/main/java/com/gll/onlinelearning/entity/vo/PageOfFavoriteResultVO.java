package com.gll.onlinelearning.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageOfFavoriteResultVO {
    private Integer id; // 收藏 id
    private String title; // 课程标题
    private String link; // 学习链接
    private Date gmtCreate; // 收藏时间
}
