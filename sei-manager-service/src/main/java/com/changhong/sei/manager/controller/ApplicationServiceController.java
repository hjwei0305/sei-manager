package com.changhong.sei.manager.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.api.ApplicationApi;
import com.changhong.sei.manager.dto.ApplicationDto;
import com.changhong.sei.manager.dto.ApplicationResponse;
import com.changhong.sei.manager.entity.Application;
import com.changhong.sei.manager.service.ApplicationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应用服务(ApplicationService)控制类
 *
 * @author sei
 * @since 2020-10-30 15:20:58
 */
@RestController
@Api(value = "ApplicationServiceApi", tags = "应用服务")
@RequestMapping(path = "application", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApplicationServiceController extends BaseEntityController<Application, ApplicationDto> implements ApplicationApi {
    /**
     * 应用服务服务对象
     */
    @Autowired
    private ApplicationService service;

    @Override
    public BaseEntityService<Application> getService() {
        return service;
    }

    /**
     * 获取当前所有可用应用服务清单
     */
    @Override
    public ResultData<List<String>> getServiceCodes() {
        return service.getServiceCodes();
    }

    /**
     * 获取当前所有可用应用服务清单
     */
    @Override
    public ResultData<List<ApplicationDto>> getServices() {
        return service.getServices();
    }

    /**
     * 获取指定应用的实例清单
     *
     * @param serviceCode 应用代码
     * @return 返回指定应用的实例清单
     */
    @Override
    public ResultData<List<ApplicationResponse>> getServiceInstance(String serviceCode) {
        return service.getServiceInstance(serviceCode);
    }
}