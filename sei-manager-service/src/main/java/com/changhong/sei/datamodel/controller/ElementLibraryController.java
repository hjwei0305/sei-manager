package com.changhong.sei.datamodel.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.api.ElementLibraryApi;
import com.changhong.sei.datamodel.dto.ElementLibraryDto;
import com.changhong.sei.datamodel.entity.ElementLibrary;
import com.changhong.sei.datamodel.service.ElementLibraryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 元素库(ElementLibrary)控制类
 *
 * @author sei
 * @since 2020-07-28 23:24:31
 */
@RestController
@Api(value = "ElementLibraryApi", tags = "元素库服务")
@RequestMapping(path = "elementLibrary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ElementLibraryController extends BaseEntityController<ElementLibrary, ElementLibraryDto>
        implements ElementLibraryApi {
    /**
     * 元素库服务对象
     */
    @Autowired
    private ElementLibraryService service;

    @Override
    public BaseEntityService<ElementLibrary> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<ElementLibraryDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}