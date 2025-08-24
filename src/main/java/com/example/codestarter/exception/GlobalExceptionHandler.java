package com.example.codestarter.exception;

import com.example.codestarter.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Map<String, Object>> handleBusinessException(BusinessException e) {
        log.error("业务异常: ", e);
        
        Map<String, Object> data = new HashMap<>();
        data.put("code", e.getCode());
        data.put("message", e.getMessage());
        
        return new Result<>(e.getCode(), e.getMessage(), data);
    }

    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Map<String, String>> handleException(Exception e) {
        log.error("系统异常: ", e);
        
        Map<String, String> data = new HashMap<>();
        data.put("error", e.getClass().getName());
        data.put("message", e.getMessage());
        
        return new Result<>(500, "系统异常: " + e.getMessage(), data);
    }
    
    /**
     * 处理 IllegalArgumentException 异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数异常: ", e);
        
        Map<String, String> data = new HashMap<>();
        data.put("error", e.getClass().getName());
        data.put("message", e.getMessage());
        
        return new Result<>(400, "参数异常: " + e.getMessage(), data);
    }
}