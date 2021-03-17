package com.changhong.sei.cicd.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.cicd.dto.DeployConfigDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 部署配置(DeployConfig)API
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@Valid
@FeignClient(name = "sei-manager", path = "deployConfig")
public interface DeployConfigApi extends BaseEntityApi<DeployConfigDto>, FindByPageApi<DeployConfigDto> {

}