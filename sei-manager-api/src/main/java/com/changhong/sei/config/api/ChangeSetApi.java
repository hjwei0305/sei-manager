package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.ChangeSetDto;
import com.changhong.sei.core.api.BaseEntityApi;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 配置变更集(ConfChangeset)API
 *
 * @author sei
 * @since 2021-02-22 21:44:02
 */
@Valid
@FeignClient(name = "sei-manager", path = ChangeSetApi.PATH)
public interface ChangeSetApi extends BaseEntityApi<ChangeSetDto> {
    String PATH = "changeSet";

}