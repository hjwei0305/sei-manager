package com.changhong.sei.datamodel.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.BaseTreeApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.datamodel.dto.DataModelTypeDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据模型分类(DataModelType)API
 *
 * @author sei
 * @since 2020-07-28 23:24:00
 */
@Valid
@FeignClient(name = "mdms", path = "dataModelType")
public interface DataModelTypeApi extends BaseEntityApi<DataModelTypeDto>, BaseTreeApi<DataModelTypeDto> {

    /**
     * 获取数据模型类型的树
     *
     * @return 获取数据模型类型的树
     */
    @GetMapping(path = "getModelTypeTree")
    @ApiOperation(value = "获取数据模型类型的树", notes = "获取数据模型类型的树")
    ResultData<List<DataModelTypeDto>> getModelTypeTree();

    /**
     * 根据模型代码获取一个节点的树
     *
     * @param code 节点code
     * @return 节点树
     */
    @GetMapping(path = "getTreeByCode")
    @ApiOperation(value = "获取一个节点的树", notes = "获取一个节点的树")
    ResultData<DataModelTypeDto> getTreeByCode(@RequestParam("code") String code);
}