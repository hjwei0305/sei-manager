package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.AppConfigDto;
import com.changhong.sei.core.api.BaseEntityApi;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

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

}