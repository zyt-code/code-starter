package com.example.codestarter.service;

import com.example.codestarter.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 基础 Service 接口
 * 定义通用的业务操作方法
 * 
 * @param <T>  实体类型，需要继承 BaseEntity
 * @param <ID> 主键类型
 */
public interface BaseService<T extends BaseEntity, ID> {

    /**
     * 保存实体（新增或更新）
     * 
     * @param entity 实体对象
     * @return 保存后的实体
     */
    T save(T entity);

    /**
     * 批量保存实体
     * 
     * @param entities 实体列表
     * @return 保存后的实体列表
     */
    List<T> saveAll(List<T> entities);

    /**
     * 根据 ID 物理删除
     * 
     * @param id 主键ID
     */
    void deleteById(ID id);

    /**
     * 批量物理删除
     * 
     * @param ids 主键ID列表
     */
    void deleteByIds(List<ID> ids);

    /**
     * 根据 ID 逻辑删除
     * 
     * @param id 主键ID
     */
    void deleteByIdLogical(ID id);

    /**
     * 批量逻辑删除
     * 
     * @param ids 主键ID列表
     */
    void deleteByIdsLogical(List<ID> ids);

    /**
     * 根据 ID 查询（包含已删除数据）
     * 
     * @param id 主键ID
     * @return 实体对象
     */
    Optional<T> findById(ID id);

    /**
     * 根据 ID 查询（仅未删除数据）
     * 
     * @param id 主键ID
     * @return 实体对象
     */
    Optional<T> findByIdAndNotDeleted(ID id);

    /**
     * 查询所有未删除数据
     * 
     * @return 实体列表
     */
    List<T> findAll();

    /**
     * 分页查询未删除数据
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<T> findAllByPage(Pageable pageable);

    /**
     * 检查指定 ID 的记录是否存在
     * 
     * @param id 主键ID
     * @return 是否存在
     */
    boolean existsById(ID id);

    /**
     * 检查指定 ID 的记录是否存在且未删除
     * 
     * @param id 主键ID
     * @return 是否存在
     */
    boolean existsByIdAndNotDeleted(ID id);

    /**
     * 统计所有记录数量
     * 
     * @return 记录数量
     */
    long count();

    /**
     * 统计未删除记录数量
     * 
     * @return 记录数量
     */
    long countNotDeleted();
}