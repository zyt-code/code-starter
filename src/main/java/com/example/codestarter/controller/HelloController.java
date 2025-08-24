package com.example.codestarter.controller;

import com.example.codestarter.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 示例控制器
 * 演示 SpringDoc API 文档的使用
 */
@Tag(name = "示例接口", description = "用于演示 SpringDoc API 文档功能的示例接口")
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * 简单问候接口
     *
     * @param name 用户名
     * @return 问候语
     */
    @Operation(summary = "问候接口", description = "根据用户名返回问候语")
    @GetMapping
    public Result<String> hello(
            @Parameter(description = "用户名") 
            @RequestParam(defaultValue = "World") String name) {
        return Result.success("Hello, " + name + "!");
    }
}