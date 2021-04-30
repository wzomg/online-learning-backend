package com.gll.onlinelearning.controller;

import com.gll.onlinelearning.common.SystemConstant;
import com.gll.onlinelearning.entity.User;
import com.gll.onlinelearning.service.UserService;
import com.gll.onlinelearning.utils.RedisKeyUtil;
import com.gll.onlinelearning.utils.SystemUtil;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Producer kaptchaProducer;

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取验证码
     */
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response) {
        // 生成验证码（文本）
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 验证码的归属
        String kaptchaOwner = SystemUtil.generateUUID();
        Cookie cookie = new Cookie("kaptchaOwner", kaptchaOwner);
        // 验证码有效时间为 60 s
        cookie.setMaxAge(60);
        // 整个项目都有效
        cookie.setPath("/");
        // 发送给客户端
        response.addCookie(cookie);
        // 将验证码存入Redis
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        // redis 有效时间为 60s
        redisTemplate.opsForValue().set(redisKey, text, 60, TimeUnit.SECONDS);

        // 将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败:" + e.getMessage());
        }
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public String login(String username, String password, String code, boolean rememberme, HttpServletResponse response,
                        @CookieValue("kaptchaOwner") String kaptchaOwner) {
        // kaptchaOwner 为 uuid
        // code 为验证码
        // 检查验证码
        String kaptcha = null;
        // 若客户端传来的 cookie 没有失效，则从 redis 中取验证码
        if (StringUtils.isNotBlank(kaptchaOwner)) {
            // 验证码的 key 为 uuid， 值为 验证码
            String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        }
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            return SystemUtil.getJSONString(2010, "验证码失效或不正确!");
        }
        // 检查账号和密码
        int expiredSeconds = rememberme ? SystemConstant.REMEMBER_EXPIRED_SECONDS : SystemConstant.DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        Map<String, Object> res = new HashMap<>();
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            // cookie设置整个项目访问路径都有效
            cookie.setPath("/");
            cookie.setMaxAge(expiredSeconds);
            cookie.setHttpOnly(false);
            response.addCookie(cookie);
            res.put("userInfo", map.get("userInfo"));
            return SystemUtil.getJSONString(200, "登录成功！", res);
        } else {
            return SystemUtil.getJSONString(2011, (String) map.get("errMsg"));
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        Map<String, Object> map = userService.register(user);
        if (map.isEmpty()) {
            return SystemUtil.getJSONString(200, "注册成功");
        } else {
            return SystemUtil.getJSONString(2008, (String) map.get("errMsg"));
        }
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public void logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
    }
}
