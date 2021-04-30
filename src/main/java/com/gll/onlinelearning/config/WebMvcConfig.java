package com.gll.onlinelearning.config;

import com.gll.onlinelearning.filter.LoginTicketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// 实现WebMvcConfigurer接口用来扩展SpringMVC的功能，注意要在这个类上加上注解配置
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 使用 @Bean 方式装配
    @Bean
    public LoginTicketInterceptor loginTicketInterceptor() {
        return new LoginTicketInterceptor();
    }

    // 注册自定义的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/kaptcha", "/register", "/login", "/swagger-resources/**", "/webjars/**",
                        "/v2/**", "/swagger-ui.html/**");
    }

    //不拦截 swagger
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
