package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.EnvVariableDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 环境变量(EnvVariable)API
 *
 * @author sei
 * @since 2021-03-02 14:26:33
 */
@Valid
@FeignClient(name = "sei-manager", path = EnvVariableApi.PATH)
public interface EnvVariableApi extends BaseEntityApi<EnvVariableDto>, FindByPageApi<EnvVariableDto> {
    String PATH = "envVariable";


}