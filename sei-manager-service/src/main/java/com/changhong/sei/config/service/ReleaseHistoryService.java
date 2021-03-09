package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.ReleaseHistoryDao;
import com.changhong.sei.config.dto.ChangeType;
import com.changhong.sei.config.dto.ConfigCompareResponse;
import com.changhong.sei.config.entity.ReleaseHistory;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 发布历史(ConfReleaseHistory)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:44:11
 */
@Service("releaseHistoryService")
public class ReleaseHistoryService extends BaseEntityService<ReleaseHistory> {
    @Autowired
    private ReleaseHistoryDao dao;

    @Override
    protected BaseEntityDao<ReleaseHistory> getDao() {
        return dao;
    }

    /**
     * 应用环境配置发布版本
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 返回配置发布版本
     */
    public Set<String> getVersions(String appCode, String envCode) {
        return dao.getVersions(appCode, envCode);
    }

    /**
     * 获取上一次发布的配置项
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 返回上一次发布的配置项
     */
    public List<ReleaseHistory> getLastReleaseHistory(String appCode, String envCode) {
        return dao.getLastReleaseHistory(appCode, envCode);
    }

    /**
     * 跨环境比较已发布的配置(当前运行时态的配置)
     *
     * @param appCode    应用代码
     * @param currentEnv 当前环境代码
     * @param targetEnv  目标环境代码
     * @return 操作结果
     */
    public ResultData<Map<String, String>> crossEnvCompare(final String appCode, final String currentEnv, final String targetEnv) {
        Map<String, String> result = new HashMap<>();
        StringBuilder currentStr = new StringBuilder();
        // 当前环境发布的配置
        List<ReleaseHistory> currentConfigs = dao.getLastReleaseHistory(appCode, currentEnv);
        if (CollectionUtils.isNotEmpty(currentConfigs)) {
            currentConfigs.sort(Comparator.comparing(ReleaseHistory::getKey));
            for (ReleaseHistory config : currentConfigs) {
                currentStr.append(config.getKey()).append(" = ").append(config.getValue()).append("\n\r");
            }
        }
        result.put("currentConfig", currentStr.toString());

        StringBuilder targetStr = new StringBuilder();
        // 目标环境发布的配置
        List<ReleaseHistory> targetConfigs = dao.getLastReleaseHistory(appCode, targetEnv);
        if (CollectionUtils.isNotEmpty(targetConfigs)) {
            targetConfigs.sort(Comparator.comparing(ReleaseHistory::getKey));
            for (ReleaseHistory config : targetConfigs) {
                targetStr.append(config.getKey()).append(" = ").append(config.getValue()).append("\n\r");
            }
        }
        result.put("targetConfig", targetStr.toString());
        return ResultData.success(result);
    }

    /**
     * 跨环境比较已发布的配置(当前运行时态的配置)
     *
     * @param appCode    应用代码
     * @param currentEnv 当前环境代码
     * @param targetEnv  目标环境代码
     * @return 操作结果
     */
    public ResultData<List<ConfigCompareResponse>> crossEnvCompareResult(final String appCode, final String currentEnv, final String targetEnv) {
        // 当前环境发布的配置
        List<ReleaseHistory> currentConfigs = dao.getLastReleaseHistory(appCode, currentEnv);
        Map<String, ReleaseHistory> currentMap = currentConfigs.stream().collect(Collectors.toMap(ReleaseHistory::getKey, h -> h));
        // 目标环境发布的配置
        List<ReleaseHistory> targetConfigs = dao.getLastReleaseHistory(appCode, targetEnv);
        Map<String, ReleaseHistory> targetMap = targetConfigs.stream().collect(Collectors.toMap(ReleaseHistory::getKey, h -> h));

        ReleaseHistory current;
        ReleaseHistory target;
        // 比较结果
        ConfigCompareResponse compare;
        List<ConfigCompareResponse> compareList = new ArrayList<>();
        // 新增部分和修改部分
        for (Map.Entry<String, ReleaseHistory> entry : currentMap.entrySet()) {
            current = entry.getValue();
            compare = new ConfigCompareResponse();
            // key
            compare.setKey(current.getKey());
            // 当前值
            compare.setCurrentValue(current.getValue());

            target = targetMap.get(entry.getKey());
            if (Objects.nonNull(target)) {
                // 比较值是否一致
                if (StringUtils.equals(current.getValue(), target.getValue())) {
                    // 配置一致
                    compare.setChangeType(ChangeType.EQUALS);
                } else {
                    // 修改部分
                    compare.setChangeType(ChangeType.MODIFY);
                }
                // 目标环境值
                compare.setTargetValue(target.getValue());
                // 发布人
                compare.setPublisherAccount(target.getPublisherAccount());
                compare.setPublisherName(target.getPublisherName());
                // 发布时间
                compare.setPublishDate(target.getPublishDate());
            } else {
                // 新增部分
                compare.setChangeType(ChangeType.CREATE);
            }
            compareList.add(compare);
        }
        // 删除部分
        for (ReleaseHistory targetConfig : targetConfigs) {
            if (!currentMap.containsKey(targetConfig.getKey())) {
                compare = new ConfigCompareResponse();
                // 删除部分
                compare.setChangeType(ChangeType.DELETE);
                // key
                compare.setKey(targetConfig.getKey());
                // 目标环境值
                compare.setTargetValue(targetConfig.getValue());
                // 发布人
                compare.setPublisherAccount(targetConfig.getPublisherAccount());
                compare.setPublisherName(targetConfig.getPublisherName());
                // 发布时间
                compare.setPublishDate(targetConfig.getPublishDate());

                compareList.add(compare);
            }
        }

        compareList = compareList.stream().sorted(Comparator.comparing(ConfigCompareResponse::getKey)).collect(Collectors.toList());
        return ResultData.success(compareList);
    }
}