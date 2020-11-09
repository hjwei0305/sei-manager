package com.changhong.sei.datamodel.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.datamodel.dto.DataModelDto;
import com.changhong.sei.datamodel.dto.DataModelFieldDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据模型(DataModel)API
 *
 * @author sei
 * @since 2020-07-28 23:23:50
 */
@Valid
@FeignClient(name = "mdms", path = "dataModel")
public interface DataModelApi extends BaseEntityApi<DataModelDto>, FindByPageApi<DataModelDto> {

    /**
     * 根据模型类型代码查询数据模型
     *
     * @param typeCode 模型类型代码
     * @return 返回指定类型的数据模型集合
     */
    @GetMapping(path = "getDataModelByTypeCode")
    @ApiOperation(value = "根据模型类型代码查询数据模型", notes = "根据模型类型代码查询数据模型")
    ResultData<List<DataModelDto>> getDataModelByTypeCode(@RequestParam("typeCode") String typeCode);

    /**
     * 根据数据模型id获取模型字段清单
     *
     * @param modelId 数据模型id
     * @return 返回自定的模型字段清单
     */
    @GetMapping(path = "getDataModelFields")
    @ApiOperation(value = "根据数据模型id获取模型字段清单", notes = "根据数据模型id获取模型字段清单")
    ResultData<List<DataModelFieldDto>> getDataModelFields(@RequestParam("modelId") String modelId);

    /**
     * 添加默认审计字段
     *
     * @param modelId 数据模型id
     * @return 返回操作结果
     */
    @PostMapping(path = "addAuditFields")
    @ApiOperation(value = "添加默认审计字段", notes = "添加默认审计字段")
    ResultData<String> addAuditFields(@RequestParam("modelId") String modelId);

    /**
     * 批量添加数据模型字段
     *
     * @param fieldDtos 数据模型字段dto
     * @return 返回操作结果
     */
    @PostMapping(path = "saveModelFields")
    @ApiOperation(value = "批量添加数据模型字段", notes = "批量添加数据模型字段")
    ResultData<String> saveModelFields(@RequestBody @Valid List<DataModelFieldDto> fieldDtos);

    /**
     * 保存单个模型字段
     *
     * @param dto 模型字段
     * @return 返回操作结果
     */
    @PostMapping(path = "saveModelField")
    @ApiOperation(value = "保存单个模型字段", notes = "保存单个模型字段")
    ResultData<String> saveModelField(@RequestBody @Valid DataModelFieldDto dto);

    /**
     * 删除数据模型字段
     *
     * @param fieldIds 删除的数据模型字段id清单
     * @return 返回操作结果
     */
    @PostMapping(path = "deleteModelFields")
    @ApiOperation(value = "删除数据模型字段", notes = "删除数据模型字段")
    ResultData<String> deleteModelFields(@RequestBody List<String> fieldIds);
}