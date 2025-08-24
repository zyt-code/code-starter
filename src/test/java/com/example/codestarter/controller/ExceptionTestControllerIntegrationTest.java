package com.example.codestarter.controller;

import com.example.codestarter.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ExceptionTestController集成测试
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExceptionTestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSuccessEndpoint() {
        String url = "http://localhost:" + port + "/api/test/success";
        ResponseEntity<Result> response = restTemplate.getForEntity(url, Result.class);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getCode());
        assertEquals("操作成功", response.getBody().getMessage());
    }

    @Test
    void testExceptionEndpoint() {
        String url = "http://localhost:" + port + "/api/test/exception";
        ResponseEntity<Result> response = restTemplate.getForEntity(url, Result.class);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value()); // HTTP状态码是200，但业务状态码是500
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("系统异常"));
    }

    @Test
    void testBusinessExceptionEndpoint() {
        String url = "http://localhost:" + port + "/api/test/business-exception";
        ResponseEntity<Result> response = restTemplate.getForEntity(url, Result.class);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value()); // HTTP状态码是200，但业务状态码是500
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getCode());
        assertEquals("这是一个业务异常", response.getBody().getMessage());
    }

    @Test
    void testIllegalArgumentExceptionEndpoint() {
        String url = "http://localhost:" + port + "/api/test/illegal-argument";
        ResponseEntity<Result> response = restTemplate.getForEntity(url, Result.class);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value()); // HTTP状态码是200，但业务状态码是400
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("参数不合法"));
    }
}