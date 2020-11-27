package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.AppModuleDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 应用模块(AppModule)API
 *
 * @author sei
 * @since 2020-11-26 14:45:24
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-manager", path = "appModule")
public interface AppModuleApi extends BaseEntityApi<AppModuleDto>, FindByPageApi<AppModuleDto> {

    /**
     * 通过应用Id获取模块清单
     *
     * @param appId 应用id
     * @return 模块清单
     */
    @GetMapping(path = "findAppId")
    @ApiOperation(value = "通过应用Id获取模块清单", notes = "通过应用Id获取模块清单")
    ResultData<List<AppModuleDto>> findAppId(@RequestParam("appId") String appId);
}