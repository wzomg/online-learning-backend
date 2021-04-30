package com.gll.onlinelearning.common;

import com.gll.onlinelearning.utils.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//全局异常处理
@ControllerAdvice
@RestController
public class CommonAdvice {
    private static Logger logger = LoggerFactory.getLogger(CommonAdvice.class);

    /**
     * 原始异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleExceptionForErrorOne(Exception e, HttpServletRequest request) {
        logger.info("Exception message：{}", e.getMessage());
        logger.info("Exception from：{}", e.getCause());
        logger.info("Exception print：{}", e);
        return SystemUtil.getJSONString(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(GlobalException.class)
    String handleExceptionForErrorTwo(GlobalException e, HttpServletRequest request) {
        logger.info("MyException message：{}", e.getMessage());
        logger.info("MyException from：{}", e.getCause());
        logger.info("MyException print：{}", e);
        return SystemUtil.getJSONString(e.getCode(), e.getMessage());
    }
}
