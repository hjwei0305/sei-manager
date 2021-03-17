package com.changhong.sei.cicd.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.cicd.dto.RuntimeEnvDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * API
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@Valid
@FeignClient(name = "sei-manager", path = "env")
public interface RuntimeEnvApi extends BaseEntityApi<RuntimeEnvDto>, FindAllApi<RuntimeEnvDto> {

}