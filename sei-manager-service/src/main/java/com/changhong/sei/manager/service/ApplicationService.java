package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.manager.dao.ApplicationDao;
import com.changhong.sei.manager.dto.ApplicationDto;
import com.changhong.sei.manager.dto.ApplicationResponse;
import com.changhong.sei.manager.entity.Application;
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
 * 应用服务(Application)业务逻辑实现类
 *
 * @author sei
 * @since 2020-10-30 15:20:57
 */
@Service("applicationService")
public class ApplicationService extends BaseEntityService<Application> {
    @Autowired
    private ApplicationDao dao;

    @Override
    protected BaseEntityDao<Application> getDao() {
        return dao;
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 数据保存操作
     *
     * @param entity
     */
    @Override
    public OperateResultWithData<Application> save(Application entity) {
        return super.save(entity);
    }

    /**
     * 获取当前所有可用应用服务清单
     */
    public ResultData<List<String>> getServiceCodes() {
        return ResultData.success(discoveryClient.getServices());
    }

    /**
     * 获取当前所有可用应用服务清单
     */
    public ResultData<List<ApplicationDto>> getServices() {
        List<ApplicationDto> services = new ArrayList<>();
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
                app.setId(instance.getInstanceId());
                app.setCode(instance.getServiceId());
                URI uri = instance.getUri();
                if (Objects.nonNull(uri)) {
                    if (instance.getPort() == -1) {
                        app.setUri(uri.toString().replace(":-1", ""));
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
