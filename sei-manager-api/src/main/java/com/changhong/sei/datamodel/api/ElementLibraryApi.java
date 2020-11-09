package com.changhong.sei.datamodel.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.datamodel.dto.ElementLibraryDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 元素库(ElementLibrary)API
 *
 * @author sei
 * @since 2020-07-28 23:24:03
 */
@Valid
@FeignClient(name = "mdms", path = "elementLibrary")
public interface ElementLibraryApi extends BaseEntityApi<ElementLibraryDto>, FindByPageApi<ElementLibraryDto> {

}