package com.changhong.sei.datamodel.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.api.LabelLibraryApi;
import com.changhong.sei.datamodel.dto.LabelLibraryDto;
import com.changhong.sei.datamodel.entity.LabelLibrary;
import com.changhong.sei.datamodel.service.LabelLibraryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签库(LabelLibrary)控制类
 *
 * @author sei
 * @since 2020-07-28 23:24:32
 */
@RestController
@Api(value = "LabelLibraryApi", tags = "标签库服务")
@RequestMapping(path = "labelLibrary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LabelLibraryController extends BaseEntityController<LabelLibrary, LabelLibraryDto>
        implements LabelLibraryApi {
    /**
     * 标签库服务对象
     */
    @Autowired
    private LabelLibraryService service;

    @Override
    public BaseEntityService<LabelLibrary> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<LabelLibraryDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}