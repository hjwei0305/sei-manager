package com.changhong.sei.datamodel.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.datamodel.dto.LabelLibraryDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 标签库(LabelLibrary)API
 *
 * @author sei
 * @since 2020-07-28 23:24:04
 */
@Valid
@FeignClient(name = "mdms", path = "labelLibrary")
public interface LabelLibraryApi extends BaseEntityApi<LabelLibraryDto>, FindByPageApi<LabelLibraryDto> {

}