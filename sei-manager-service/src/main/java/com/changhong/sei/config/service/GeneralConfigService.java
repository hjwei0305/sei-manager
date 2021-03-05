package com.changhong.sei.config.service;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.dao.GeneralConfigDao;
import com.changhong.sei.config.entity.GeneralConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.SearchFilter;
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
 * 通用参数配置(GeneralConfig)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:44:04
 */
@Service
public class GeneralConfigService extends BaseEntityService<GeneralConfig> {
    @Autowired
    private GeneralConfigDao dao;

    @Override
    protected BaseEntityDao<GeneralConfig> getDao() {
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
        GeneralConfig variable = dao.findOne(s);
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
            Set<String> envConfig = configList.stream().map(g -> g.getEnvCode() + "-" + g.getKey()).collect(Collectors.toSet());
            for (GeneralConfig conf : configs) {
                if (envConfig.contains(conf.getEnvCode() + "-" + conf.getKey())) {
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
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> syncConfigs(List<GeneralConfig> configs) {
        if (CollectionUtils.isEmpty(configs)) {
            return ResultData.fail("配置数据不能为空.");
        }

        Set<String> ids = configs.stream().map(GeneralConfig::getId).collect(Collectors.toSet());
        List<GeneralConfig> configList = dao.findByFilter(new SearchFilter(GeneralConfig.ID, ids, SearchFilter.Operator.IN));
        if (CollectionUtils.isEmpty(configList)) {
            return ResultData.fail("同步的配置不存在.");
        }
        Map<String, GeneralConfig> configMap = configList.stream().collect(Collectors.toMap(GeneralConfig::getId, c -> c));
        Set<String> keys = configList.stream().map(GeneralConfig::getKey).collect(Collectors.toSet());
        configList = dao.findByFilter(new SearchFilter(GeneralConfig.FIELD_KEY, keys, SearchFilter.Operator.IN));
        Set<String> existedKeys;
        if (CollectionUtils.isEmpty(configList)) {
            existedKeys = new HashSet<>();
        } else {
            existedKeys = configList.stream().map(c -> c.getEnvCode() + "|" + c.getKey()).collect(Collectors.toSet());
        }

        String id;
        GeneralConfig conf;
        for (GeneralConfig config : configs) {
            id = config.getId();
            if (StringUtils.isBlank(id)) {
                continue;
            }
            conf = configMap.get(id);
            if (Objects.isNull(conf)) {
                continue;
            }
            // 检查key是否在指定环境中存在,存在则跳过不同步
            if (StringUtils.isBlank(config.getEnvCode()) || existedKeys.contains(config.getEnvCode() + "|" + conf.getKey())) {
                continue;
            }

            config.setId(null);
            config.setKey(conf.getKey());
            config.setValue(conf.getValue());
            config.setRemark(conf.getRemark());
            this.save(config);
        }
        return ResultData.success();
    }
}