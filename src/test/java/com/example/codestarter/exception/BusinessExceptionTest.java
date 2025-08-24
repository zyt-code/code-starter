package com.example.codestarter.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * BusinessException单元测试
 */
class BusinessExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "业务异常";
        BusinessException exception = new BusinessException(message);
        
        assertEquals(Integer.valueOf(500), exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithCodeAndMessage() {
        Integer code = 400;
        String message = "参数错误";
        BusinessException exception = new BusinessException(code, message);
        
        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
    }
}