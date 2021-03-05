package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.AppConfigDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

/**
 * 应用参数配置(ConfAppConfig)API
 *
 * @author sei
 * @since 2021-02-22 21:43:53
 */
@Valid
@FeignClient(name = "sei-manager", path = AppConfigApi.PATH)
public interface AppConfigApi extends BaseEntityApi<AppConfigDto> {
    String PATH = "appConfig";

    /**
     * 通过环境代码获取应用配置清单
     *
     * @param envCode 环境代码
     * @return 业务实体
     */
    @GetMapping(path = "findByEnv")
    @ApiOperation(value = "通过环境代码获取指定环境的应用配置清单", notes = "通过环境代码获取指定环境的应用配置清单")
    ResultData<List<AppConfigDto>> findByEnv(@RequestParam("envCode") String envCode);

    /**
     * 新增应用配置
     *
     * @param dtoList 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "addConfig", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "新增应用配置", notes = "新增应用配置")
    ResultData<Void> addConfig(@RequestBody @NotEmpty Set<AppConfigDto> dtoList);

    /**
     * 启用应用配置
     *
     * @param ids 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "enableConfig", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "启用应用配置", notes = "启用应用配置")
    ResultData<Void> enableConfig(@RequestBody @NotEmpty Set<String> ids);

    /**
     * 禁用应用配置
     *
     * @param ids 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "disableConfig", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "禁用应用配置", notes = "禁用应用配置")
    ResultData<Void> disableConfig(@RequestBody @NotEmpty Set<String> ids);

    /**
     * 同步配置到其他环境
     *
     * @param dtoList 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "syncConfigs", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "同步配置到其他环境", notes = "同步配置到其他环境")
    ResultData<Void> syncConfigs(@RequestBody @Valid Set<AppConfigDto> dtoList);
}