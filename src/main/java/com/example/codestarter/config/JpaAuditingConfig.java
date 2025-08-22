package com.example.codestarter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA 审计配置
 * 启用自动审计功能，自动填充创建时间和更新时间
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}