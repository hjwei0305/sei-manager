package com.changhong.sei.config.service;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.dao.GeneralConfigDao;
import com.changhong.sei.config.dto.EnvConfigDto;
import com.changhong.sei.config.dto.GeneralConfigDto;
import com.changhong.sei.config.entity.GeneralConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.service.RuntimeEnvService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Set;


/**
 * 通用参数配置(GeneralConfig)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:44:04
 */
@Service("GeneralConfigService")
public class GeneralConfigService extends BaseEntityService<GeneralConfig> {
    @Autowired
    private GeneralConfigDao dao;
    @Autowired
    private RuntimeEnvService runtimeEnvService;

    @Override
    protected BaseEntityDao<GeneralConfig> getDao() {
        return dao;
    }

    /**
     * 新增通用配置
     *
     * @param configs 业务实体
     * @return 操作结果
     */
    public ResultData<Void> addGeneralConfig(Set<GeneralConfig> configs) {
        if (CollectionUtils.isEmpty(configs)) {
            return ResultData.fail("配置数据不能为空.");
        }


        return null;
    }

    /**
     * 禁用通用配置
     *
     * @param ids 业务实体DTO
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> updateStatus(Set<String> ids, UseStatus useStatus) {
        dao.updateStatus(ids, useStatus);
        return ResultData.success();
    }

    /**
     * 同步配置到其他环境
     *
     * @param ids 业务实体DTO
     * @return 操作结果
     */
    public ResultData<Void> syncConfigs(Set<EnvConfigDto> ids) {
        return null;
    }
}