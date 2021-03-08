package com.changhong.sei.config.controller;

import com.changhong.sei.config.api.ReleaseHistoryApi;
import com.changhong.sei.config.dto.ConfigCompareResponse;
import com.changhong.sei.config.dto.ReleaseHistoryDto;
import com.changhong.sei.config.entity.ReleaseHistory;
import com.changhong.sei.config.service.ReleaseHistoryService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 发布历史(ConfReleaseHistory)控制类
 *
 * @author sei
 * @since 2021-02-22 21:44:12
 */
@RestController
@Api(value = "ConfReleaseHistoryApi", tags = "发布历史服务")
@RequestMapping(path = ReleaseHistoryApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ReleaseHistoryController extends BaseEntityController<ReleaseHistory, ReleaseHistoryDto> implements ReleaseHistoryApi {
    /**
     * 发布历史服务对象
     */
    @Autowired
    private ReleaseHistoryService service;

    @Override
    public BaseEntityService<ReleaseHistory> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<ReleaseHistoryDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }

    /**
     * 获取指定应用环境的发布版本清单
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 发布版本清单
     */
    @Override
    public ResultData<Set<String>> getVersions(String appCode, String envCode) {
        return ResultData.success(service.getVersions(appCode, envCode));
    }

    /**
     * 跨环境比较已发布的配置(当前运行时态的配置)
     *
     * @param appCode    应用代码
     * @param currentEnv 当前环境代码
     * @param targetEnv  目标环境代码
     * @return 操作结果
     */
    @Override
    public ResultData<List<ConfigCompareResponse>> crossEnvCompare(String appCode, String currentEnv, String targetEnv) {
        return service.crossEnvCompare(appCode, currentEnv, targetEnv);
    }
}