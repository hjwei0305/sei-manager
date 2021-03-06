package com.changhong.sei.config.service;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.dao.AppConfigDao;
import com.changhong.sei.config.dto.ConfigCompareResponse;
import com.changhong.sei.config.entity.*;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 应用参数配置(ConfAppConfig)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:43:46
 */
@Service
public class AppConfigService extends BaseEntityService<AppConfig> {
    @Autowired
    private AppConfigDao dao;
    @Autowired
    private GeneralConfigService generalConfigService;
    @Autowired
    private EnvVariableService envVariableService;
    @Autowired
    private ReleasedConfigService releasedConfigService;
    @Autowired
    private ReleaseHistoryService releaseHistoryService;

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
        AppConfig config = dao.findOne(s);
        if (Objects.isNull(config)) {
            return OperateResult.operationFailure("不存在删除的对象.");
        }
        if (UseStatus.NONE == config.getUseStatus()) {
            return super.delete(s);
        } else if (UseStatus.ENABLE == config.getUseStatus()) {
            // 更新为禁用
            config.setUseStatus(UseStatus.DISABLE);
            dao.save(config);
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
        Set<String> keys = configs.stream().map(c -> c.getAppCode() + "|" + c.getKey()).collect(Collectors.toSet());
        if (keys.size() != 1) {
            return ResultData.fail("不能同时配置多个key.");
        }
        AppConfig config = configs.get(0);
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(AppConfig.FIELD_KEY, config.getKey()));
        search.addFilter(new SearchFilter(AppConfig.FIELD_APP_CODE, config.getAppCode()));
        List<AppConfig> configList = dao.findByFilters(search);
        if (CollectionUtils.isEmpty(configList)) {
            this.save(configs);
        } else {
            // 已存在的环境配置
            Set<String> envConfig = configList.stream().map(g -> g.getEnvCode() + "|" + g.getKey()).collect(Collectors.toSet());
            for (AppConfig conf : configs) {
                if (envConfig.contains(conf.getEnvCode() + "|" + conf.getKey())) {
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
    public ResultData<Void> syncConfigs(List<AppConfig> configs) {
        if (CollectionUtils.isEmpty(configs)) {
            return ResultData.fail("配置数据不能为空.");
        }

        Set<String> ids = configs.stream().map(AppConfig::getId).collect(Collectors.toSet());
        List<AppConfig> configList = dao.findByFilter(new SearchFilter(AppConfig.ID, ids, SearchFilter.Operator.IN));
        if (CollectionUtils.isEmpty(configList)) {
            return ResultData.fail("同步的配置不存在.");
        }
        // 检查是否是同一个应用,即appCode相同
        Set<String> appCodes = configList.stream().map(AppConfig::getAppCode).collect(Collectors.toSet());
        if (appCodes.size() != 1) {
            return ResultData.fail("不允许同步多个应用的配置.");
        }
        // id与config映射
        Map<String, AppConfig> configMap = configList.stream().collect(Collectors.toMap(AppConfig::getId, c -> c));
        // 需要同步的key
        Set<String> keys = configList.stream().map(AppConfig::getKey).collect(Collectors.toSet());

        // 查询指定应用与所有要同步key已存在的配置
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(AppConfig.FIELD_APP_CODE, configList.get(0).getAppCode()));
        search.addFilter(new SearchFilter(AppConfig.FIELD_KEY, keys, SearchFilter.Operator.IN));
        configList = dao.findByFilters(search);

        Set<String> existedKeys;
        if (CollectionUtils.isEmpty(configList)) {
            existedKeys = new HashSet<>();
        } else {
            existedKeys = configList.stream().map(c -> c.getEnvCode() + "|" + c.getKey()).collect(Collectors.toSet());
        }

        String id;
        AppConfig conf;
        for (AppConfig config : configs) {
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


    /**
     * 发布前比较配置
     *
     * @param appCode 应用代码
     * @return 操作结果
     */
    public ResultData<ConfigCompareResponse> compareBeforeRelease(String appCode, String envCode) {
        return null;
    }

    /**
     * 发布配置
     *
     * @param appCode 应用代码
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> release(final String appCode, final String envCode) {
        Set<ReleasedConfig> configSet = new HashSet<>();
        // 获取所有可用的环境变量
        List<EnvVariableValue> variableValues = envVariableService.getEnableVariableValues(envCode);
        // 环境变量key-value映射
        Map<String, String> variableValueMap = variableValues.stream()
                .collect(Collectors.toMap(v -> "${".concat(v.getKey()).concat("}"), EnvVariableValue::getValue));

        // 获取可用的通用配置清单
        List<GeneralConfig> generalConfigs = generalConfigService.getEnableConfigs(envCode);
        Set<ReleasedConfig> gcSet = generalConfigs.stream().map(gc -> {
            ReleasedConfig config = new ReleasedConfig();
            config.setAppCode(appCode);
            config.setEnvCode(envCode);
            config.setKey(gc.getKey());
            // 处理环境变量
            config.setValue(resolutionVariable(gc.getValue(), variableValueMap));
            return config;
        }).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(gcSet)) {
            configSet.addAll(gcSet);
        }

        // 获取可用的应用自定义配置清单
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(AppConfig.FIELD_APP_CODE, appCode));
        search.addFilter(new SearchFilter(AppConfig.FIELD_ENV_CODE, envCode));
        search.addFilter(new SearchFilter(AppConfig.FIELD_USE_STATUS, UseStatus.ENABLE));
        List<AppConfig> appConfigs = dao.findByFilters(search);
        Set<ReleasedConfig> acSet = appConfigs.stream().map(ac -> {
            ReleasedConfig config = new ReleasedConfig();
            config.setAppCode(appCode);
            config.setEnvCode(envCode);
            config.setKey(ac.getKey());
            // 处理环境变量
            config.setValue(resolutionVariable(ac.getValue(), variableValueMap));
            return config;
        }).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(acSet)) {
            configSet.addAll(acSet);
        }

        if (CollectionUtils.isEmpty(configSet)) {
            return ResultData.fail("不存在可用的配置.");
        }

        // 清除原有发布配置
        releasedConfigService.removeByEnvAppCode(envCode, appCode);

        // 写入发布配置
        releasedConfigService.save(configSet);

        LocalDateTime now = LocalDateTime.now();
        String version = DateUtils.formatDate(new Date(), DateUtils.FULL_SEQ_FORMAT);
        ReleaseHistory history;
        SessionUser user = ContextUtil.getSessionUser();
        List<ReleaseHistory> histories = new ArrayList<>();
        // 记录发布历史
        for (ReleasedConfig config : configSet) {
            history = new ReleaseHistory();
            history.setVersion(version);
            history.setAppCode(config.getAppCode());
            history.setEnvCode(config.getEnvCode());
            history.setKey(config.getKey());
            history.setValue(config.getValue());
            history.setPublisherAccount(user.getAccount());
            history.setPublisherName(user.getUserName());
            history.setPublishDate(now);
            histories.add(history);
        }
        releaseHistoryService.save(histories);


        return ResultData.success();
    }

    /**
     * 解析环境变量
     * 将${ABC} 替换为具体的值
     *
     * @param value            配置值,如${ABC}替换为123
     * @param variableValueMap 环境变量映射值
     * @return 返回具体的值
     */
    private String resolutionVariable(String value, Map<String, String> variableValueMap) {
        if (StringUtils.isNotBlank(value)) {
            for (Map.Entry<String, String> entry : variableValueMap.entrySet()) {
                if (value.contains(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return value;
    }
}