package com.changhong.sei.config.controller;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.api.GeneralConfigApi;
import com.changhong.sei.config.dto.GeneralConfigDto;
import com.changhong.sei.config.entity.GeneralConfig;
import com.changhong.sei.config.service.GeneralConfigService;
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
 * 通用参数配置(GeneralConfig)控制类
 *
 * @author sei
 * @since 2021-02-22 21:44:06
 */
@RestController
@Api(value = "GeneralConfigApi", tags = "通用参数配置服务")
@RequestMapping(path = GeneralConfigApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class GeneralConfigController extends BaseEntityController<GeneralConfig, GeneralConfigDto> implements GeneralConfigApi {
    /**
     * 全局参数配置服务对象
     */
    @Autowired
    private GeneralConfigService service;

    @Override
    public BaseEntityService<GeneralConfig> getService() {
        return service;
    }

    /**
     * 通过环境代码获取通用配置清单
     *
     * @param envCode 环境代码
     * @return 业务实体
     */
    @Override
    public ResultData<List<GeneralConfigDto>> findByEnv(String envCode) {
        List<GeneralConfig> configList = service.findListByProperty(GeneralConfig.FIELD_ENV_CODE, envCode);
        List<GeneralConfigDto> list = configList.stream().map(c -> dtoModelMapper.map(c, GeneralConfigDto.class)).collect(Collectors.toList());
        return ResultData.success(list);
    }

    /**
     * 新增通用配置
     *
     * @param dtoList 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> addGeneralConfig(Set<GeneralConfigDto> dtoList) {
        List<GeneralConfig> configs = dtoList.stream().map(c -> entityModelMapper.map(c, GeneralConfig.class)).collect(Collectors.toList());
        return service.addGeneralConfig(configs);
    }

    /**
     * 启用通用配置
     *
     * @param ids 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> enableConfig(Set<String> ids) {
        return service.updateStatus(ids, UseStatus.ENABLE);
    }

    /**
     * 禁用通用配置
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
    public ResultData<Void> syncConfigs(List<GeneralConfigDto> dtoList) {
        List<GeneralConfig> configs = dtoList.stream().map(c -> entityModelMapper.map(c, GeneralConfig.class)).collect(Collectors.toList());
        return service.syncConfigs(configs);
    }
}