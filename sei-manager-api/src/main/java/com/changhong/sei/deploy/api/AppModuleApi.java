package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.deploy.dto.AppModuleDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 应用模块(AppModule)API
 *
 * @author sei
 * @since 2020-11-26 14:45:24
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-manager", path = "appModule")
public interface AppModuleApi extends BaseEntityApi<AppModuleDto> {

}