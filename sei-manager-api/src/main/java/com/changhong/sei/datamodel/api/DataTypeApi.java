package com.changhong.sei.datamodel.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.datamodel.dto.DataTypeDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 数据类型(DataType)API
 *
 * @author sei
 * @since 2020-07-28 23:24:03
 */
@Valid
@FeignClient(name = "mdms", path = "dataType")
public interface DataTypeApi extends BaseEntityApi<DataTypeDto>, FindByPageApi<DataTypeDto> {

}