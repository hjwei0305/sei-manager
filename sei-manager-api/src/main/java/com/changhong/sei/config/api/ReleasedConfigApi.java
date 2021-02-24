package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.ReleasedConfigDto;
import com.changhong.sei.core.api.BaseEntityApi;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 已发布的应用配置(ConfReleasedConfig)API
 *
 * @author sei
 * @since 2021-02-22 21:44:21
 */
@Valid
@FeignClient(name = "sei-manager", path = ReleasedConfigApi.PATH)
public interface ReleasedConfigApi extends BaseEntityApi<ReleasedConfigDto> {
    String PATH = "releasedConfig";

}