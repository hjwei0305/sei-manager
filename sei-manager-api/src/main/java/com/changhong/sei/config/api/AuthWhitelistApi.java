package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.AuthWhitelistDto;
import com.changhong.sei.config.dto.SyncAuthWhitelistDto;
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
import java.util.List;

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
     * 同步配置到其他环境
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "sync", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "同步认证白名单到其他环境", notes = "同步认证白名单到其他环境")
    ResultData<Void> syncConfigs(@Valid @RequestBody SyncAuthWhitelistDto dto);

    /**
     * 发布
     *
     * @param env 环境代码
     * @return 操作结果
     */
    @PostMapping(path = "publish")
    @ApiOperation(value = "发布", notes = "发布")
    ResultData<Void> publish(@RequestParam("env") String env);
}