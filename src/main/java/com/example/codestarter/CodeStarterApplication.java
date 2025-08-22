package com.example.codestarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用程序主启动类
 */
@SpringBootApplication
public class CodeStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeStarterApplication.class, args);
        System.out.println("==================================================");
        System.out.println("  Code Starter Application Started Successfully! ");
        System.out.println("  Access URL: http://localhost:8080/api         ");
        System.out.println("==================================================");
    }
}