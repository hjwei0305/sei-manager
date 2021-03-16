package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.AppConfigDto;
import com.changhong.sei.config.dto.AuthWhitelistDto;
import com.changhong.sei.config.dto.GeneralConfigDto;
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
 * 网关认证白名单(AuthWhitelist)API
 *
 * @author sei
 * @since 2021-02-22 21:44:02
 */
@Valid
@FeignClient(name = "sei-manager", path = AuthWhitelistApi.PATH)
public interface AuthWhitelistApi extends BaseEntityApi<AuthWhitelistDto> {
    String PATH = "authWhitelist";

    /**
     * 获取指定环境的白名单
     *
     * @param envCode 环境代码
     * @return 业务实体
     */
    @GetMapping(path = "get")
    @ApiOperation(value = "获取指定环境的白名单", notes = "获取指定环境的白名单")
    ResultData<List<AuthWhitelistDto>> get(@RequestParam("envCode") String envCode);

    /**
     * 通过应用和环境代码获取应用认证白名单
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 业务实体
     */
    @GetMapping(path = "findByAppEnv")
    @ApiOperation(value = "通过应用和环境代码获取应用认证白名单", notes = "通过应用和环境代码获取应用认证白名单")
    ResultData<List<AuthWhitelistDto>> findByAppEnv(@RequestParam("appCode") String appCode, @RequestParam("envCode") String envCode);

    /**
     * 新增认证白名单
     *
     * @param dtoList 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "新增认证白名单", notes = "新增认证白名单")
    ResultData<Void> add(@RequestBody @NotEmpty List<AuthWhitelistDto> dtoList);

    /**
     * 同步配置到其他环境
     *
     * @param dtoList 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "sync", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "同步认证白名单到其他环境", notes = "同步认证白名单到其他环境")
    ResultData<Void> syncConfigs(@RequestBody @Valid List<AuthWhitelistDto> dtoList);
}