package com.changhong.sei.manager.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.manager.dto.ApplicationResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-30 13:48
 */
@Service
public class ApplicationService {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取当前所有可用应用服务清单
     */
    public ResultData<List<String>> getServiceCodes() {
        return ResultData.success(discoveryClient.getServices());
    }

    /**
     * 获取当前所有可用应用服务清单
     */
    public ResultData<List<ApplicationResponse>> getServices() {
        List<ApplicationResponse> services = new ArrayList<>();
        List<String> list = discoveryClient.getServices();
        ApplicationResponse app;
        for (String code : list) {
            app = new ApplicationResponse();
            app.setCode(code);
            app.setName(code);
            services.add(app);
        }
        return ResultData.success(services);
    }

    /**
     * 获取指定应用的实例清单
     *
     * @param serviceCode 应用代码
     * @return 返回指定应用的实例清单
     */
    public ResultData<List<ApplicationResponse>> getServiceInstance(String serviceCode) {
        List<ApplicationResponse> applications = new ArrayList<>();
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceCode);
        if (CollectionUtils.isNotEmpty(instances)) {
            ApplicationResponse app;
            for (ServiceInstance instance : instances) {
                app = new ApplicationResponse();
                app.setInstanceId(instance.getInstanceId());
                app.setCode(instance.getServiceId());
                URI uri = instance.getUri();
                if (Objects.nonNull(uri)) {
                    if (instance.getPort() == -1) {
                        String temp = uri.getHost();
                        app.setUri(temp.replace(temp, ":-1"));
                    } else {
                        app.setUri(uri.getHost());
                    }
                }
                app.setMetadata(instance.getMetadata());
                applications.add(app);
            }
        }
        return ResultData.success(applications);
    }
}
