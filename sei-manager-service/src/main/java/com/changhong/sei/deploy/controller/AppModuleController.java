package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.api.AppModuleApi;
import com.changhong.sei.deploy.dto.AppModuleDto;
import com.changhong.sei.deploy.entity.AppModule;
import com.changhong.sei.deploy.service.AppModuleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用模块(AppModule)控制类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@RestController
@Api(value = "AppModuleApi", tags = "应用模块服务")
@RequestMapping(path = "appModule", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AppModuleController extends BaseEntityController<AppModule, AppModuleDto> implements AppModuleApi {
    /**
     * 应用模块服务对象
     */
    @Autowired
    private AppModuleService service;

    @Override
    public BaseEntityService<AppModule> getService() {
        return service;
    }

}