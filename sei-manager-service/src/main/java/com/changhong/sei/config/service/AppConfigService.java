package com.changhong.sei.config.service;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.dao.AppConfigDao;
import com.changhong.sei.config.entity.AppConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 应用参数配置(ConfAppConfig)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:43:46
 */
@Service("appConfigService")
public class AppConfigService extends BaseEntityService<AppConfig> {
    @Autowired
    private AppConfigDao dao;

    @Override
    protected BaseEntityDao<AppConfig> getDao() {
        return dao;
    }

    /**
     * 主键删除
     *
     * @param s 主键
     * @return 返回操作结果对象
     */
    @Override
    @Transactional
    public OperateResult delete(String s) {
        AppConfig variable = dao.findOne(s);
        if (Objects.isNull(variable)) {
            return OperateResult.operationFailure("不存在删除的对象.");
        }
        if (UseStatus.NONE == variable.getUseStatus()) {
            return super.delete(s);
        } else if (UseStatus.ENABLE == variable.getUseStatus()) {
            // 更新为禁用
            variable.setUseStatus(UseStatus.DISABLE);
            dao.save(variable);
        } else {
            return OperateResult.operationFailure("对象已为禁用状态.");
        }
        return OperateResult.operationSuccess();
    }

    /**
     * 新增通用配置
     *
     * @param configs 业务实体
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> addConfig(List<AppConfig> configs) {
        if (CollectionUtils.isEmpty(configs)) {
            return ResultData.fail("配置数据不能为空.");
        }
        Set<String> keys = configs.stream().map(AppConfig::getKey).collect(Collectors.toSet());
        if (keys.size() != 1) {
            return ResultData.fail("不能同时配置多个key.");
        }
        String key = keys.stream().findAny().get();
        List<AppConfig> configList = dao.findListByProperty(AppConfig.FIELD_KEY, key);
        if (CollectionUtils.isEmpty(configList)) {
            this.save(configs);
        } else {
            // 已存在的环境配置
            Set<String> envConfig = configList.stream().map(AppConfig::getEnvCode).collect(Collectors.toSet());
            for (AppConfig conf : configs) {
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

    /**
     * 同步配置到其他环境
     *
     * @param configs 业务实体DTO
     * @return 操作结果
     */
    public ResultData<Void> syncConfigs(List<AppConfig> configs) {
        if (CollectionUtils.isEmpty(configs)) {
            return ResultData.fail("配置数据不能为空.");
        }
        String id;
        AppConfig conf;
        Map<String, AppConfig> configMap = new HashMap<>();
        for (AppConfig config : configs) {
            id = config.getId();
            if (StringUtils.isBlank(id)) {
                continue;
            }
            conf = configMap.get(id);
            if (Objects.isNull(conf)) {
                conf = dao.findOne(id);
                if (Objects.isNull(conf)) {
                    continue;
                }
                configMap.put(id, conf);
            }
            config.setKey(conf.getKey());
            config.setValue(conf.getValue());
            config.setRemark(conf.getRemark());
        }
        return addConfig(configs);
    }
}