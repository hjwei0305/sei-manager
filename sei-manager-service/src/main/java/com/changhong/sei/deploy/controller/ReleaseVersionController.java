package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.api.ReleaseVersionApi;
import com.changhong.sei.deploy.dto.ReleaseVersionDto;
import com.changhong.sei.deploy.entity.ReleaseVersion;
import com.changhong.sei.deploy.service.ReleaseVersionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 版本发布记录(ReleaseVersion)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@RestController
@Api(value = "ReleaseVersionApi", tags = "版本发布记录服务")
@RequestMapping(path = "releaseVersion", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReleaseVersionController extends BaseEntityController<ReleaseVersion, ReleaseVersionDto> implements ReleaseVersionApi {
    /**
     * 服务器节点服务对象
     */
    @Autowired
    private ReleaseVersionService service;

    @Override
    public BaseEntityService<ReleaseVersion> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<ReleaseVersionDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}