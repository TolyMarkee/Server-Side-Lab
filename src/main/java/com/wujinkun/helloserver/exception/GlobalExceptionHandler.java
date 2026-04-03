package com.wujinkun.helloserver.exception;

import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.common.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器（实验扩展：统一处理所有Controller层异常，返回标准Result）
 */
@RestControllerAdvice // 核心注解：全局捕获Controller异常
public class GlobalExceptionHandler {

    /**
     * 处理所有未知异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 打印异常栈（用于排查问题，生产环境可优化）
        e.printStackTrace();
        return Result.error(ResultCode.ERROR);
    }

    /**
     * 处理参数错误异常（可选扩展）
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return Result.error(ResultCode.PARAM_ERROR.getCode(), e.getMessage());
    }
}