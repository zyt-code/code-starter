package com.example.codestarter.service.impl;

import com.example.codestarter.entity.BaseEntity;
import com.example.codestarter.repository.BaseRepository;
import com.example.codestarter.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 基础 Service 实现类
 * 提供通用的业务操作实现，集成事务管理
 * 
 * @param <T>  实体类型，需要继承 BaseEntity
 * @param <ID> 主键类型
 */
@Slf4j
public abstract class BaseServiceImpl<T extends BaseEntity, ID> implements BaseService<T, ID> {

    @Autowired
    protected BaseRepository<T, ID> repository;

    /**
     * 保存实体（新增或更新）
     * 
     * @param entity 实体对象
     * @return 保存后的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T save(T entity) {
        log.debug("保存实体: {}", entity);
        T savedEntity = repository.save(entity);
        log.debug("保存成功，ID: {}", savedEntity.getId());
        return savedEntity;
    }

    /**
     * 批量保存实体
     * 
     * @param entities 实体列表
     * @return 保存后的实体列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public List<T> saveAll(List<T> entities) {
        log.debug("批量保存实体，数量: {}", entities.size());
        List<T> savedEntities = repository.saveAll(entities);
        log.debug("批量保存成功，数量: {}", savedEntities.size());
        return savedEntities;
    }

    /**
     * 根据 ID 物理删除
     * 
     * @param id 主键ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(ID id) {
        log.debug("物理删除实体，ID: {}", id);
        repository.deleteById(id);
        log.debug("物理删除成功，ID: {}", id);
    }

    /**
     * 批量物理删除
     * 
     * @param ids 主键ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public void deleteByIds(List<ID> ids) {
        log.debug("批量物理删除实体，数量: {}", ids.size());
        repository.deleteAllById(ids);
        log.debug("批量物理删除成功，数量: {}", ids.size());
    }

    /**
     * 根据 ID 逻辑删除
     * 
     * @param id 主键ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIdLogical(ID id) {
        log.debug("逻辑删除实体，ID: {}", id);
        repository.logicalDeleteById(id);
        log.debug("逻辑删除成功，ID: {}", id);
    }

    /**
     * 批量逻辑删除
     * 
     * @param ids 主键ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public void deleteByIdsLogical(List<ID> ids) {
        log.debug("批量逻辑删除实体，数量: {}", ids.size());
        repository.logicalDeleteByIds(ids);
        log.debug("批量逻辑删除成功，数量: {}", ids.size());
    }

    /**
     * 根据 ID 查询（包含已删除数据）
     * 
     * @param id 主键ID
     * @return 实体对象
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        log.debug("根据ID查询实体: {}", id);
        return repository.findById(id);
    }

    /**
     * 根据 ID 查询（仅未删除数据）
     * 
     * @param id 主键ID
     * @return 实体对象
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<T> findByIdAndNotDeleted(ID id) {
        log.debug("根据ID查询未删除实体: {}", id);
        return repository.findByIdAndDeleted(id);
    }

    /**
     * 查询所有未删除数据
     * 
     * @return 实体列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        log.debug("查询所有未删除实体");
        List<T> entities = repository.findAllByDeletedOrderByCreateTimeDesc();
        log.debug("查询结果数量: {}", entities.size());
        return entities;
    }

    /**
     * 分页查询未删除数据
     * 
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    @Transactional(readOnly = true)
    public Page<T> findAllByPage(Pageable pageable) {
        log.debug("分页查询未删除实体，页码: {}, 大小: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<T> page = repository.findAllByDeletedFalse(pageable);
        log.debug("分页查询结果，总数: {}, 当前页数量: {}", page.getTotalElements(), page.getNumberOfElements());
        return page;
    }

    /**
     * 检查指定 ID 的记录是否存在
     * 
     * @param id 主键ID
     * @return 是否存在
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        log.debug("检查实体是否存在，ID: {}", id);
        boolean exists = repository.existsById(id);
        log.debug("实体存在性检查结果: {}", exists);
        return exists;
    }

    /**
     * 检查指定 ID 的记录是否存在且未删除
     * 
     * @param id 主键ID
     * @return 是否存在
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByIdAndNotDeleted(ID id) {
        log.debug("检查未删除实体是否存在，ID: {}", id);
        boolean exists = repository.existsByIdAndDeletedFalse(id);
        log.debug("未删除实体存在性检查结果: {}", exists);
        return exists;
    }

    /**
     * 统计所有记录数量
     * 
     * @return 记录数量
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        log.debug("统计所有实体数量");
        long count = repository.count();
        log.debug("所有实体数量: {}", count);
        return count;
    }

    /**
     * 统计未删除记录数量
     * 
     * @return 记录数量
     */
    @Override
    @Transactional(readOnly = true)
    public long countNotDeleted() {
        log.debug("统计未删除实体数量");
        long count = repository.countByDeletedFalse();
        log.debug("未删除实体数量: {}", count);
        return count;
    }
}