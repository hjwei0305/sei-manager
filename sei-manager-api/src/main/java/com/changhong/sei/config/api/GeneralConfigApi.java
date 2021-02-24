package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.GeneralConfigDto;
import com.changhong.sei.core.api.BaseEntityApi;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 全局参数配置(ConfGlobalConfig)API
 *
 * @author sei
 * @since 2021-02-22 21:44:08
 */
@Valid
@FeignClient(name = "sei-manager", path = GeneralConfigApi.PATH)
public interface GeneralConfigApi extends BaseEntityApi<GeneralConfigDto> {
    String PATH = "generalConfig";

}