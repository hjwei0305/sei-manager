package com.changhong.sei.config.controller;

import com.changhong.sei.config.api.ReleasedConfigApi;
import com.changhong.sei.config.dto.ReleasedConfigDto;
import com.changhong.sei.config.entity.ReleasedConfig;
import com.changhong.sei.config.service.ReleasedConfigService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 已发布的应用配置(ConfReleasedConfig)控制类
 *
 * @author sei
 * @since 2021-02-22 21:44:18
 */
@RestController
@Api(value = "ConfReleasedConfigApi", tags = "已发布的应用配置服务")
@RequestMapping(path = ReleasedConfigApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ReleasedConfigController extends BaseEntityController<ReleasedConfig, ReleasedConfigDto> implements ReleasedConfigApi {
    /**
     * 已发布的应用配置服务对象
     */
    @Autowired
    private ReleasedConfigService service;

    @Override
    public BaseEntityService<ReleasedConfig> getService() {
        return service;
    }

}