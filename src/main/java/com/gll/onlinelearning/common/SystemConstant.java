package com.gll.onlinelearning.common;

public class SystemConstant {

    /**
     * 默认状态的登录凭证的超时时间（12小时）
     */
    public static int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 记住状态的登录凭证超时时间（100天）
     */
    public static int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * 权限: 普通用户
     */
    public static String AUTHORITY_USER = "user";

    /**
     * 权限: 管理员
     */
    public static String AUTHORITY_ADMIN = "admin";

    /**
     * 每页最多显示10条数据
     */
    public static Integer PAGE_SIZE = 5;
}
