package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.deploy.dto.ReleaseVersionDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 版本发布记录(ReleaseVersion)API
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@Valid
@FeignClient(name = "sei-manager", path = "releaseVersion")
public interface ReleaseVersionApi extends BaseEntityApi<ReleaseVersionDto>, FindByPageApi<ReleaseVersionDto> {

}