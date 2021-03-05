package com.changhong.sei.config.controller;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.api.AppConfigApi;
import com.changhong.sei.config.dto.AppConfigDto;
import com.changhong.sei.config.entity.AppConfig;
import com.changhong.sei.config.service.AppConfigService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 应用参数配置(ConfAppConfig)控制类
 *
 * @author sei
 * @since 2021-02-22 21:43:48
 */
@RestController
@Api(value = "ConfAppConfigApi", tags = "应用参数配置服务")
@RequestMapping(path = AppConfigApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class AppConfigController extends BaseEntityController<AppConfig, AppConfigDto> implements AppConfigApi {
    /**
     * 应用参数配置服务对象
     */
    @Autowired
    private AppConfigService service;

    @Override
    public BaseEntityService<AppConfig> getService() {
        return service;
    }

    /**
     * 通过环境代码获取应用配置清单
     *
     * @param envCode 环境代码
     * @return 业务实体
     */
    @Override
    public ResultData<List<AppConfigDto>> findByEnv(String envCode) {
        List<AppConfig> configList = service.findListByProperty(AppConfig.FIELD_ENV_CODE, envCode);
        List<AppConfigDto> list = configList.stream().map(c -> dtoModelMapper.map(c, AppConfigDto.class)).collect(Collectors.toList());
        return ResultData.success(list);
    }

    /**
     * 新增应用配置
     *
     * @param dtoList 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> addConfig(Set<AppConfigDto> dtoList) {
        List<AppConfig> configs = dtoList.stream().map(c -> entityModelMapper.map(c, AppConfig.class)).collect(Collectors.toList());
        return service.addConfig(configs);
    }

    /**
     * 启用应用配置
     *
     * @param ids 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> enableConfig(Set<String> ids) {
        return service.updateStatus(ids, UseStatus.ENABLE);
    }

    /**
     * 禁用应用配置
     *
     * @param ids 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> disableConfig(Set<String> ids) {
        return service.updateStatus(ids, UseStatus.DISABLE);
    }

    /**
     * 同步配置到其他环境
     *
     * @param dtoList 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> syncConfigs(Set<AppConfigDto> dtoList) {
        List<AppConfig> configs = dtoList.stream().map(c -> entityModelMapper.map(c, AppConfig.class)).collect(Collectors.toList());
        return service.syncConfigs(configs);
    }
}