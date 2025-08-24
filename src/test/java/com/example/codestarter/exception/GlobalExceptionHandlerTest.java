package com.example.codestarter.exception;

import com.example.codestarter.common.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GlobalExceptionHandler单元测试
 */
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleBusinessException() {
        BusinessException businessException = new BusinessException("业务异常测试");
        Result<Map<String, Object>> result = globalExceptionHandler.handleBusinessException(businessException);

        assertNotNull(result);
        assertEquals(Integer.valueOf(500), result.getCode());
        assertEquals("业务异常测试", result.getMessage());
        assertNotNull(result.getData());
    }

    @Test
    void testHandleBusinessExceptionWithCode() {
        BusinessException businessException = new BusinessException(400, "参数错误");
        Result<Map<String, Object>> result = globalExceptionHandler.handleBusinessException(businessException);

        assertNotNull(result);
        assertEquals(Integer.valueOf(400), result.getCode());
        assertEquals("参数错误", result.getMessage());
        assertNotNull(result.getData());
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("参数不合法");
        Result<Map<String, String>> result = globalExceptionHandler.handleIllegalArgumentException(illegalArgumentException);

        assertNotNull(result);
        assertEquals(Integer.valueOf(400), result.getCode());
        assertTrue(result.getMessage().contains("参数不合法"));
        assertNotNull(result.getData());
    }

    @Test
    void testHandleException() {
        Exception exception = new Exception("系统异常");
        Result<Map<String, String>> result = globalExceptionHandler.handleException(exception);

        assertNotNull(result);
        assertEquals(Integer.valueOf(500), result.getCode());
        assertTrue(result.getMessage().contains("系统异常"));
        assertNotNull(result.getData());
    }
}