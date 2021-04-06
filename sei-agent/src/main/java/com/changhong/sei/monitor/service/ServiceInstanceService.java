package com.changhong.sei.monitor.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.monitor.dto.ServiceInstanceDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 23:23
 */
@Service
public class ServiceInstanceService {

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
    public ResultData<List<ServiceInstanceDto>> getServices() {
        List<ServiceInstanceDto> services = new ArrayList<>();
        List<String> list = discoveryClient.getServices();
        for (String code : list) {
            services.add(new ServiceInstanceDto(code, code));
        }
        return ResultData.success(services);
    }

    /**
     * 获取指定应用的实例清单
     *
     * @param serviceCode 应用代码
     * @return 返回指定应用的实例清单
     */
    public ResultData<List<ServiceInstanceDto>> getServiceInstance(String serviceCode) {
        List<ServiceInstanceDto> applications = new ArrayList<>();
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceCode);
        if (CollectionUtils.isNotEmpty(instances)) {
            ServiceInstanceDto app;
            for (ServiceInstance instance : instances) {
                app = new ServiceInstanceDto(instance.getServiceId(), instance.getServiceId());
                app.setInstanceId(instance.getInstanceId());
                app.setHost(instance.getHost());
                app.setPort(instance.getPort());
                app.setUri(instance.getUri().toString());
                app.setMetadata(instance.getMetadata());
                applications.add(app);
            }
        }
        return ResultData.success(applications);
    }
}
