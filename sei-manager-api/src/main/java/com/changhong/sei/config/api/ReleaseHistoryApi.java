package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.ReleaseHistoryDto;
import com.changhong.sei.core.api.BaseEntityApi;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 发布历史(ConfReleaseHistory)API
 *
 * @author sei
 * @since 2021-02-22 21:44:15
 */
@Valid
@FeignClient(name = "sei-manager", path = ReleaseHistoryApi.PATH)
public interface ReleaseHistoryApi extends BaseEntityApi<ReleaseHistoryDto> {
    String PATH = "releaseHistory";

}