package com.example.codestarter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * 包含公共字段：id、创建时间、更新时间、逻辑删除标志
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /**
     * 主键ID，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标志
     * 0: 未删除, 1: 已删除
     */
    @Column(name = "deleted", nullable = false)
    private Integer deleted = 0;

    /**
     * 创建者ID
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 更新者ID
     */
    @Column(name = "update_by")
    private Long updateBy;

    /**
     * 版本号，用于乐观锁
     */
    @Version
    @Column(name = "version", nullable = false)
    private Integer version = 1;

    @PrePersist
    protected void onCreate() {
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
        if (updateTime == null) {
            updateTime = LocalDateTime.now();
        }
        if (deleted == null) {
            deleted = 0;
        }
        if (version == null) {
            version = 1;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}