package com.gll.onlinelearning.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserVO {
    private Integer id; //用户id
    private String username; //用户名
    private String avatar; //用户头像
}
