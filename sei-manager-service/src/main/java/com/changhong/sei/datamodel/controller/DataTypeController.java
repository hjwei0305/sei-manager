package com.changhong.sei.datamodel.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.api.DataTypeApi;
import com.changhong.sei.datamodel.dto.DataTypeDto;
import com.changhong.sei.datamodel.entity.DataType;
import com.changhong.sei.datamodel.service.DataTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据类型(DataType)控制类
 *
 * @author sei
 * @since 2020-07-28 23:24:29
 */
@RestController
@Api(value = "DataTypeApi", tags = "数据类型服务")
@RequestMapping(path = "dataType", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataTypeController extends BaseEntityController<DataType, DataTypeDto>
        implements DataTypeApi {
    /**
     * 数据类型服务对象
     */
    @Autowired
    private DataTypeService service;

    @Override
    public BaseEntityService<DataType> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<DataTypeDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}