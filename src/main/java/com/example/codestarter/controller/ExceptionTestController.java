package com.example.codestarter.controller;

import com.example.codestarter.common.Result;
import com.example.codestarter.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常处理测试控制器
 */
@RestController
@RequestMapping("/api/test")
public class ExceptionTestController {

    /**
     * 测试正常返回
     */
    @GetMapping("/success")
    public Result<String> success() {
        return Result.success("操作成功");
    }

    /**
     * 测试系统异常
     */
    @GetMapping("/exception")
    public Result<String> exception() {
        int i = 1 / 0; // 故意制造异常
        return Result.success("不会执行到这里");
    }

    /**
     * 测试业务异常
     */
    @GetMapping("/business-exception")
    public Result<String> businessException() {
        throw new BusinessException("这是一个业务异常");
    }

    /**
     * 测试参数异常
     */
    @GetMapping("/illegal-argument")
    public Result<String> illegalArgument() {
        throw new IllegalArgumentException("参数不合法");
    }
}