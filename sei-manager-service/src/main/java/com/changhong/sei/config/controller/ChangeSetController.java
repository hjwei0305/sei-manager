package com.changhong.sei.config.controller;

import com.changhong.sei.config.api.ChangeSetApi;
import com.changhong.sei.config.dto.ChangeSetDto;
import com.changhong.sei.config.entity.ChangeSet;
import com.changhong.sei.config.service.ChangeSetService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置变更集(ConfChangeset)控制类
 *
 * @author sei
 * @since 2021-02-22 21:43:59
 */
@RestController
@Api(value = "ConfChangesetApi", tags = "配置变更集服务")
@RequestMapping(path = ChangeSetApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChangeSetController extends BaseEntityController<ChangeSet, ChangeSetDto> implements ChangeSetApi {
    /**
     * 配置变更集服务对象
     */
    @Autowired
    private ChangeSetService service;

    @Override
    public BaseEntityService<ChangeSet> getService() {
        return service;
    }

}