package com.changhong.sei.config.service;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.common.YamlTransferUtils;
import com.changhong.sei.config.dao.AppConfigDao;
import com.changhong.sei.config.dto.AppDto;
import com.changhong.sei.config.dto.ChangeType;
import com.changhong.sei.config.dto.ConfigCompareResponse;
import com.changhong.sei.config.entity.*;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.dto.serach.SearchOrder;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.ge.entity.AppModule;
import com.changhong.sei.ge.entity.RuntimeEnv;
import com.changhong.sei.ge.service.AppModuleService;
import com.changhong.sei.ge.service.RuntimeEnvService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private AppModuleService appModuleService;
    @Autowired
    private RuntimeEnvService runtimeEnvService;

    @Override
    protected BaseEntityDao<AppConfig> getDao() {
        return dao;
    }

    /**
     * 获取应用清单
     *
     * @return 应用清单
     */
    public ResultData<List<AppDto>> getAppList(String groupCode) {
        List<AppDto> appDtoList;
        List<AppModule> appModules;
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(AppModule.FIELD_NAME_SPACE, SearchFilter.NO_NULL_VALUE));
        search.addFilter(new SearchFilter(AppModule.FROZEN, Boolean.FALSE));
        if (StringUtils.isNotBlank(groupCode)) {
            search.addFilter(new SearchFilter(AppModule.FIELD_GROUP_CODE, groupCode));
        }
        search.addSortOrder(new SearchOrder(AppModule.FIELD_CODE));
        appModules = appModuleService.findByFilters(search);
        if (CollectionUtils.isNotEmpty(appModules)) {
            appDtoList = appModules.stream()
                    // 命名空间不为空,则是后端应用
                    .filter(a -> StringUtils.isNotBlank(a.getNameSpace()))
                    .map(a -> {
                AppDto dto = new AppDto();
                dto.setCode(a.getCode());
                dto.setName(a.getName());
                return dto;
            }).sorted(Comparator.comparing(AppDto::getCode)).collect(Collectors.toList());
        } else {
            appDtoList = new ArrayList<>();
        }
        return ResultData.success(appDtoList);
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
    public ResultData<List<ConfigCompareResponse>> compareBeforeRelease(final String appCode, final String envCode) {
        // 获取要发布的配置清单
        ResultData<Set<ReleasedConfig>> resultData = getReleasedConfigs(appCode, envCode, "");
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }
        // 本次要发布的配置
        Set<ReleasedConfig> configSet = resultData.getData();
        Map<String, ReleasedConfig> releasedConfigMap = configSet.stream().collect(Collectors.toMap(ReleasedConfig::getKey, c -> c));

        // 上次发布的配置
        List<ReleaseHistory> histories = releaseHistoryService.getLastReleaseHistory(appCode, envCode);
        Map<String, ReleaseHistory> historyMap = histories.stream().collect(Collectors.toMap(ReleaseHistory::getKey, h -> h));

        ReleasedConfig config;
        ReleaseHistory history;
        // 比较结果
        ConfigCompareResponse compare;
        List<ConfigCompareResponse> compareList = new ArrayList<>();
        // 新增部分和修改部分
        for (Map.Entry<String, ReleasedConfig> entry : releasedConfigMap.entrySet()) {
            config = entry.getValue();
            compare = new ConfigCompareResponse();
            // key
            compare.setKey(config.getKey());
            // 发布后值
            compare.setTargetValue(config.getValue());

            history = historyMap.get(entry.getKey());
            if (Objects.nonNull(history)) {
                // 比较值是否一致
                if (StringUtils.equals(config.getValue(), history.getValue())) {
                    continue;
                }
                // 修改部分
                compare.setChangeType(ChangeType.MODIFY);
                // 本次发布前的值
                compare.setCurrentValue(history.getValue());
                // 发布人
                compare.setPublisherAccount(history.getPublisherAccount());
                compare.setPublisherName(history.getPublisherName());
                // 发布时间
                compare.setPublishDate(history.getPublishDate());
            } else {
                // 新增部分
                compare.setChangeType(ChangeType.CREATE);
            }
            compareList.add(compare);
        }
        // 删除部分
        for (ReleaseHistory his : histories) {
            if (!releasedConfigMap.containsKey(his.getKey())) {
                compare = new ConfigCompareResponse();
                // 删除部分
                compare.setChangeType(ChangeType.DELETE);
                // key
                compare.setKey(his.getKey());
                // 本次发布前的值
                compare.setCurrentValue(his.getValue());
                // 发布人
                compare.setPublisherAccount(his.getPublisherAccount());
                compare.setPublisherName(his.getPublisherName());
                // 发布时间
                compare.setPublishDate(his.getPublishDate());

                compareList.add(compare);
            }
        }

        if (CollectionUtils.isNotEmpty(compareList)) {
            compareList = compareList.stream().sorted(Comparator.comparing(ConfigCompareResponse::getKey)).collect(Collectors.toList());
        } else {
            return ResultData.fail("不存在配置差异,无需发布.");
        }
        return ResultData.success(compareList);
    }

    /**
     * 发布配置
     *
     * @param appCode 应用代码
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> release(final String appCode, final String envCode) {
        // 发布版本(批号)
        LocalDateTime now = LocalDateTime.now();
        final String version = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        ResultData<Set<ReleasedConfig>> resultData = getReleasedConfigs(appCode, envCode, version);
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }
        // 获取要发布的配置清单
        Set<ReleasedConfig> configSet = resultData.getData();

        // 清除原有发布配置
        releasedConfigService.removeByEnvAppCode(envCode, appCode);

        // 写入发布配置
        releasedConfigService.save(configSet);

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
     * 获取yaml格式
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return yaml格式
     */
    public String getYamlData(final String appCode, final String envCode) {
        String result = "";
        // 获取可用的应用自定义配置清单
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(AppConfig.FIELD_APP_CODE, appCode));
        search.addFilter(new SearchFilter(AppConfig.FIELD_ENV_CODE, envCode));
        search.addFilter(new SearchFilter(AppConfig.FIELD_USE_STATUS, UseStatus.ENABLE));
        List<AppConfig> appConfigs = dao.findByFilters(search);
        if (CollectionUtils.isNotEmpty(appConfigs)) {
            // 获取所有可用的环境变量
            List<EnvVariableValue> variableValues = envVariableService.getEnableVariableValues(envCode);
            // 环境变量key-value映射
            final Map<String, String> variableValueMap = variableValues.stream()
                    .collect(Collectors.toMap(v -> "${".concat(v.getKey()).concat("}"), EnvVariableValue::getValue));
            StringBuilder str = new StringBuilder();
            for (AppConfig ac : appConfigs) {
                // 处理环境变量
                str.append(ac.getKey()).append(" = ").append(resolutionVariable(ac.getValue(), variableValueMap)).append("\n\r");
            }
//            Map<String, Object> dataMap = appConfigs.stream().collect(Collectors.toMap(AppConfig::getKey, ac -> {
//                // 处理环境变量
//                return resolutionVariable(ac.getValue(), variableValueMap);
//            }));
            result = YamlTransferUtils.properties2Yaml(str.toString());
        }

        return result;
    }

    /**
     * 保存yaml格式配置
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @param yaml    yaml格式配置内容
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> saveYamlData(final String appCode, final String envCode, String yaml) {
        AppModule module = appModuleService.findByProperty(AppModule.FIELD_CODE, appCode);
        if (Objects.isNull(module)) {
            return ResultData.fail("应用模块中未找到代码[" + appCode + "].");
        }
        RuntimeEnv env = runtimeEnvService.findByProperty(RuntimeEnv.CODE_FIELD, envCode);
        if (Objects.isNull(env)) {
            return ResultData.fail("运行环境中未找到代码[" + envCode + "].");
        }

        Map<String, Object> dataMap = YamlTransferUtils.yaml2Map(yaml);
        if (dataMap.isEmpty()) {
            return ResultData.fail("yaml解析未获取到配置数据.");
        }

        // 查询指定应用所有可用的配置
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(AppConfig.FIELD_APP_CODE, appCode));
        search.addFilter(new SearchFilter(AppConfig.FIELD_ENV_CODE, envCode));
        search.addFilter(new SearchFilter(AppConfig.FIELD_USE_STATUS, UseStatus.ENABLE));
        List<AppConfig> configList = dao.findByFilters(search);
        Map<String, AppConfig> existedKeyMap;
        if (CollectionUtils.isEmpty(configList)) {
            existedKeyMap = new HashMap<>();
        } else {
            existedKeyMap = configList.stream().collect(Collectors.toMap(AppConfig::getKey, a -> a));
        }

        AppConfig config;
        // 处理删除
        for (Map.Entry<String, AppConfig> entry : existedKeyMap.entrySet()) {
            if (!dataMap.containsKey(entry.getKey())) {
                config = entry.getValue();
                config.setUseStatus(UseStatus.DISABLE);
                this.save(config);
            }
        }

        // 处理新增和修改
        List<AppConfig> configs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String value = (String) entry.getValue();
            // 检查key是否在指定环境中存在,存在则跳过不同步
            config = existedKeyMap.get(entry.getKey());
            if (Objects.isNull(config)) {
                config = new AppConfig();
                config.setAppCode(appCode);
                config.setAppName(module.getName());
                config.setEnvCode(envCode);
                config.setEnvName(env.getName());
                config.setKey(entry.getKey());
                config.setUseStatus(UseStatus.ENABLE);
            } else {
                if (StringUtils.equals(config.getValue(), value)) {
                    continue;
                }
            }
            config.setValue(value);
            configs.add(config);
        }
        if (CollectionUtils.isNotEmpty(configs)) {
            this.save(configs);
        }
        return ResultData.success();
    }

    /**
     * 获取要发布的配置清单
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @param version 版本呢
     * @return 要发布的配置清单
     */
    private ResultData<Set<ReleasedConfig>> getReleasedConfigs(final String appCode, final String envCode, final String version) {
        Set<ReleasedConfig> configSet = new HashSet<>();
        // 获取所有可用的环境变量
        List<EnvVariableValue> variableValues = envVariableService.getEnableVariableValues(envCode);
        // 环境变量key-value映射
        Map<String, String> variableValueMap = variableValues.stream()
                .collect(Collectors.toMap(v -> "${".concat(v.getKey()).concat("}"), EnvVariableValue::getValue));

        // 获取可用的应用自定义配置清单
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(AppConfig.FIELD_APP_CODE, appCode));
        search.addFilter(new SearchFilter(AppConfig.FIELD_ENV_CODE, envCode));
        search.addFilter(new SearchFilter(AppConfig.FIELD_USE_STATUS, UseStatus.ENABLE));
        List<AppConfig> appConfigs = dao.findByFilters(search);
        for (AppConfig ac : appConfigs) {
            ReleasedConfig config = new ReleasedConfig();
            config.setVersion(version);
            config.setAppCode(appCode);
            config.setEnvCode(envCode);
            config.setKey(ac.getKey());
            // 处理环境变量
            config.setValue(resolutionVariable(ac.getValue(), variableValueMap));
            configSet.add(config);
        }

        // 获取可用的通用配置清单
        List<GeneralConfig> generalConfigs = generalConfigService.getEnableConfigs(envCode);
        for (GeneralConfig gc : generalConfigs) {
            ReleasedConfig config = new ReleasedConfig();
            config.setVersion(version);
            config.setAppCode(appCode);
            config.setEnvCode(envCode);
            config.setKey(gc.getKey());
            // 处理环境变量
            config.setValue(resolutionVariable(gc.getValue(), variableValueMap));
            if (!configSet.add(config)) {
                LogUtil.debug(gc.getKey() + " -> 被应用配置覆盖.");
            }
        }

        if (CollectionUtils.isEmpty(configSet)) {
            return ResultData.fail("不存在可用的配置.");
        }
        return ResultData.success(configSet);
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
        if (StringUtils.isNotBlank(value) && value.contains("${") && variableValueMap.size() > 0) {
            for (Map.Entry<String, String> entry : variableValueMap.entrySet()) {
                if (value.contains(entry.getKey())) {
                    return value.replace(entry.getKey(), entry.getValue());
                }
            }
        }
        return value;
    }
}