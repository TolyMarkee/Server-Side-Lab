package com.wujinkun.helloserver.exception;

import com.wujinkun.helloserver.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // 拦截工程中所有异常
    @ExceptionHandler(Exception.class)
    public Result<String> handleAllException(Exception e) {
        // 打印异常信息（方便开发调试，实验可保留）
        e.printStackTrace();
        // 返回统一错误响应，携带异常信息
        return Result.error(500, "服务器异常：" + e.getMessage());
    }
}