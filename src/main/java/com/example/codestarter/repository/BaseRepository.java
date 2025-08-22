package com.example.codestarter.repository;

import com.example.codestarter.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 基础 Repository 接口
 * 提供通用的数据访问方法，包括逻辑删除支持
 * 
 * @param <T>  实体类型，需要继承 BaseEntity
 * @param <ID> 主键类型
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {

    /**
 * 查询未删除的所有记录，按创建时间倒序
     * 
     * @return 未删除的记录列表
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = 0 ORDER BY e.createTime DESC")
    List<T> findAllByDeletedOrderByCreateTimeDesc();

    /**
     * 查询未删除的记录（分页）
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = 0")
    Page<T> findAllByDeletedFalse(Pageable pageable);

    /**
     * 根据 ID 查询未删除的记录
     * 
     * @param id 主键ID
     * @return 实体对象
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deleted = 0")
    Optional<T> findByIdAndDeleted(@Param("id") ID id);

    /**
     * 检查指定 ID 的记录是否存在且未删除
     * 
     * @param id 主键ID
     * @return 是否存在
     */
    @Query("SELECT COUNT(e) > 0 FROM #{#entityName} e WHERE e.id = :id AND e.deleted = 0")
    boolean existsByIdAndDeletedFalse(@Param("id") ID id);

    /**
     * 逻辑删除指定 ID 的记录
     * 
     * @param id 主键ID
     */
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = 1, e.updateTime = CURRENT_TIMESTAMP WHERE e.id = :id")
    void logicalDeleteById(@Param("id") ID id);

    /**
     * 批量逻辑删除
     * 
     * @param ids 主键ID列表
     */
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = 1, e.updateTime = CURRENT_TIMESTAMP WHERE e.id IN :ids")
    void logicalDeleteByIds(@Param("ids") List<ID> ids);

    /**
     * 统计未删除的记录数量
     * 
     * @return 记录数量
     */
    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.deleted = 0")
    long countByDeletedFalse();
}