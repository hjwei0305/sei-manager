package com.changhong.sei.config.controller;

import com.changhong.sei.config.api.ReleaseHistoryApi;
import com.changhong.sei.config.dto.ReleaseHistoryDto;
import com.changhong.sei.config.entity.ReleaseHistory;
import com.changhong.sei.config.service.ReleaseHistoryService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发布历史(ConfReleaseHistory)控制类
 *
 * @author sei
 * @since 2021-02-22 21:44:12
 */
@RestController
@Api(value = "ConfReleaseHistoryApi", tags = "发布历史服务")
@RequestMapping(path = ReleaseHistoryApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ReleaseHistoryController extends BaseEntityController<ReleaseHistory, ReleaseHistoryDto> implements ReleaseHistoryApi {
    /**
     * 发布历史服务对象
     */
    @Autowired
    private ReleaseHistoryService service;

    @Override
    public BaseEntityService<ReleaseHistory> getService() {
        return service;
    }

}