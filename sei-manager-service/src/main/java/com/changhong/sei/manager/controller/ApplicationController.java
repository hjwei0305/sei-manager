package com.changhong.sei.manager.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.manager.api.ApplicationApi;
import com.changhong.sei.manager.api.HelloApi;
import com.changhong.sei.manager.dto.ApplicationResponse;
import com.changhong.sei.manager.service.ApplicationService;
import com.changhong.sei.manager.service.HelloService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 应用服务API服务实现
 */
@RestController
@Api(value = "ApplicationApi", tags = "应用API服务")
@RequestMapping(path = "application", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApplicationController implements ApplicationApi {
    @Autowired
    private ApplicationService service;

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
    public ResultData<List<ApplicationResponse>> getServices() {
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
