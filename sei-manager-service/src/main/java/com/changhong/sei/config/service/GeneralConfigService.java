package com.changhong.sei.config.service;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.dao.GeneralConfigDao;
import com.changhong.sei.config.entity.GeneralConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.service.RuntimeEnvService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> addGeneralConfig(List<GeneralConfig> configs) {
        if (CollectionUtils.isEmpty(configs)) {
            return ResultData.fail("配置数据不能为空.");
        }
        Set<String> keys = configs.stream().map(GeneralConfig::getKey).collect(Collectors.toSet());
        if (keys.size() != 1) {
            return ResultData.fail("不能同时配置多个key.");
        }
        String key = keys.stream().findAny().get();
        List<GeneralConfig> configList = dao.findListByProperty(GeneralConfig.FIELD_KEY, key);
        if (CollectionUtils.isEmpty(configList)) {
            this.save(configs);
        } else {
            // 已存在的环境配置
            Set<String> envConfig = configList.stream().map(GeneralConfig::getEnvCode).collect(Collectors.toSet());
            for (GeneralConfig conf : configs) {
                if (envConfig.contains(conf.getEnvCode())) {
                    continue;
                }
                this.save(conf);
            }
        }

        return ResultData.success();
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
}