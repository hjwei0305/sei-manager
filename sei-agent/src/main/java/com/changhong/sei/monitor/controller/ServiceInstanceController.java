package com.changhong.sei.monitor.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.monitor.api.ServiceInstanceApi;
import com.changhong.sei.monitor.dto.ServiceInstanceDto;
import com.changhong.sei.monitor.service.ServiceInstanceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能：服务实例
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 23:23
 */
@RestController
@RefreshScope
@Api(value = "ServiceInstanceApi", tags = "服务实例API服务")
@RequestMapping(path = "serviceInstance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ServiceInstanceController implements ServiceInstanceApi {
    /**
     * 应用服务服务对象
     */
    @Autowired
    private ServiceInstanceService service;

    /**
     * 获取当前所有可用应用服务清单
     */
    @Override
    public ResultData<List<ServiceInstanceDto>> getServices() {
        return service.getServices();
    }

    /**
     * 获取指定应用的实例清单
     *
     * @param serviceCode 应用代码
     * @return 返回指定应用的实例清单
     */
    @Override
    public ResultData<List<ServiceInstanceDto>> getServiceInstance(String serviceCode) {
        return service.getServiceInstance(serviceCode);
    }
}
