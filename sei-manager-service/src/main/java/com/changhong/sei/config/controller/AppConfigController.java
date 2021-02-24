package com.changhong.sei.config.controller;

import com.changhong.sei.config.api.AppConfigApi;
import com.changhong.sei.config.dto.AppConfigDto;
import com.changhong.sei.config.entity.AppConfig;
import com.changhong.sei.config.service.AppConfigService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用参数配置(ConfAppConfig)控制类
 *
 * @author sei
 * @since 2021-02-22 21:43:48
 */
@RestController
@Api(value = "ConfAppConfigApi", tags = "应用参数配置服务")
@RequestMapping(path = AppConfigApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class AppConfigController extends BaseEntityController<AppConfig, AppConfigDto> implements AppConfigApi {
    /**
     * 应用参数配置服务对象
     */
    @Autowired
    private AppConfigService service;

    @Override
    public BaseEntityService<AppConfig> getService() {
        return service;
    }

}