package com.gll.onlinelearning.utils;


public class RedisKeyUtil {

    private static final String SPLIT = ":";

    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER = "user";
    private static final String PREFIX_SUBJECT = "subject";


    // 登录验证码
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    // 登录凭证
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    // 用户信息key
    public static String getUserKey(Integer userId) {
        return PREFIX_USER + SPLIT + userId;
    }

    //课程信息key
    public static String getSubjectKey() {
        return PREFIX_SUBJECT + SPLIT + "all";
    }
}