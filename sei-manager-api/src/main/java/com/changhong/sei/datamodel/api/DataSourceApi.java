package com.changhong.sei.datamodel.api;

import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.datamodel.dto.DBTypeDto;
import com.changhong.sei.datamodel.dto.DataSourceDto;
import com.changhong.sei.datamodel.dto.DataSourceRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据源(DataSource)API
 *
 * @author sei
 * @since 2020-07-28 23:24:02
 */
@Valid
@FeignClient(name = "mdms", path = "dataSource")
public interface DataSourceApi extends FindByPageApi<DataSourceDto> {

    /**
     * 保存数据源实体
     *
     * @param dto 数据源实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "saveRequest", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存数据源实体", notes = "保存一个数据源实体")
    ResultData<DataSourceDto> saveRequest(@RequestBody @Valid DataSourceRequest dto);

    /**
     * 删除数据源实体
     *
     * @param id 数据源实体Id
     * @return 操作结果
     */
    @DeleteMapping(path = "delete/{id}")
    @ApiOperation(value = "删除数据源实体", notes = "删除一个数据源实体")
    ResultData<String> delete(@PathVariable("id") String id);

    /**
     * 通过Id获取一个数据源实体
     *
     * @param id 数据源实体Id
     * @return 业务实体
     */
    @GetMapping(path = "findOne")
    @ApiOperation(value = "获取一个数据源实体", notes = "通过Id获取一个数据源实体")
    ResultData<DataSourceDto> findOne(@RequestParam("id") String id);

    /**
     * 获取所有支持的数据库类型
     */
    @GetMapping(path = "getDBTypes")
    @ApiOperation(value = "获取所有支持的数据库类型", notes = "获取所有支持的数据库类型")
    ResultData<List<DBTypeDto>> getDBTypes();
}