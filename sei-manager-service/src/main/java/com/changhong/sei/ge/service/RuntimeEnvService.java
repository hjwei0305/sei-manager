package com.changhong.sei.ge.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.ge.dao.RuntimeEnvDao;
import com.changhong.sei.ge.entity.RuntimeEnv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("runtimeEnvService")
@CacheConfig(cacheNames = "sei:manager:env")
public class RuntimeEnvService extends BaseEntityService<RuntimeEnv> {
    @Autowired
    private RuntimeEnvDao dao;

    @Override
    protected BaseEntityDao<RuntimeEnv> getDao() {
        return dao;
    }

    /**
     * 数据保存操作
     *
     * @param entity
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public OperateResultWithData<RuntimeEnv> save(RuntimeEnv entity) {
        return super.save(entity);
    }

    /**
     * 主键删除
     *
     * @param s 主键
     * @return 返回操作结果对象
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public OperateResult delete(String s) {
        return super.delete(s);
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    @Cacheable(key = "'Unfrozen'")
    public List<RuntimeEnv> findAllUnfrozen() {
        return super.findAllUnfrozen();
    }

    @Cacheable(key = "#code")
    public RuntimeEnv findByCode(String code) {
        return dao.findByProperty(RuntimeEnv.CODE_FIELD, code);
    }


}