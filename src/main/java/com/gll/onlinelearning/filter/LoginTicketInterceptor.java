package com.gll.onlinelearning.filter;


import com.alibaba.fastjson.JSONObject;
import com.gll.onlinelearning.common.HostHolder;
import com.gll.onlinelearning.entity.LoginTicket;
import com.gll.onlinelearning.entity.User;
import com.gll.onlinelearning.service.UserService;
import com.gll.onlinelearning.utils.CookieUtil;
import com.gll.onlinelearning.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@Slf4j
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    @Resource
    private HostHolder hostHolder;

    /**
     * 调用时间：Controller方法处理之前
     * 执行顺序：链式 Interceptor 情况下，Interceptor 按照声明的顺序一个接一个执行
     * 若返回 false，则中断执行，注意：不会进入 afterCompletion
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginTicketInterceptor -> preHandle，url：" + request.getServletPath());
        // 从 cookie 中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");
        if (ticket != null) {
            // 查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                User user = userService.getUserById(loginTicket.getUserId());
                // 在本次请求中持有用户，将用户暂存到某个线程中
                hostHolder.setUser(user);
                log.info("LoginTicketInterceptor -> preHandle，loginTicket：" + loginTicket);
                return true;
            }
        }
        ResponseUtil.out(response, new JSONObject() {{
            put("code", 2000);
            put("msg", "你还没有登录哦！");
        }});
        return false;
    }

    // 在 controller 之后，模板之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 调用前提：preHandle 返回 true
     * 调用时间：DispatcherServlet 进行视图的渲染之后
     * 作用：多用于清理资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
        hostHolder.clear();
    }
}
