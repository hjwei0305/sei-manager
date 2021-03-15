package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.AuthWhitelistDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping(path = "getWhitelist")
    @ApiOperation(value = "获取指定环境的白名单", notes = "获取指定环境的白名单")
    ResultData<List<AuthWhitelistDto>> getWhitelist(@RequestParam("envCode") String envCode);
}