package com.gll.onlinelearning.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LoginTicket {
    /**
     * 用户主键 id
     */
    private Integer userId;
    /**
     * 随机字符串
     */
    private String ticket;
    /**
     * 登录凭证状态（0-有效; 1-无效）
     */
    private int status;
    /**
     * 凭证过期时间
     */
    private Date expired;
}
