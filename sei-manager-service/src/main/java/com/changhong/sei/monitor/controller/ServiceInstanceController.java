package com.changhong.sei.monitor.controller;

import com.changhong.sei.common.TokenInterceptor;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.ge.entity.RuntimeEnv;
import com.changhong.sei.ge.service.RuntimeEnvService;
import com.changhong.sei.monitor.api.ServiceInstanceApi;
import com.changhong.sei.monitor.dto.ServiceInstanceDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * 实现功能：服务实例
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 23:23
 */
@RestController
@RefreshScope
@Api(value = "ServiceInstanceApi", tags = "服务实例API服务")
@RequestMapping(path = "serviceInstance", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceInstanceController implements ServiceInstanceApi {

    @Autowired
    private RuntimeEnvService runtimeEnvService;

    /**
     * 获取当前所有可用应用服务清单
     */
    @Override
    public ResultData<List<ServiceInstanceDto>> getServices(String env) {
        RuntimeEnv runtimeEnv = runtimeEnvService.findByCode(env);
        if (Objects.isNull(runtimeEnv)) {
            return ResultData.fail("运行环境中未找到代码[" + env + "].");
        }
        RestTemplate restTemplate = new RestTemplate();
        //向restTemplate中添加自定义的拦截器
        restTemplate.getInterceptors().add(new TokenInterceptor());

        String baseUrl = runtimeEnv.getAgentServer();
        return restTemplate.getForObject(baseUrl + "/serviceInstance/getServices", ResultData.class);
    }

    /**
     * 获取指定应用的实例清单
     *
     * @param serviceCode 应用代码
     * @return 返回指定应用的实例清单
     */
    @Override
    public ResultData<List<ServiceInstanceDto>> getServiceInstance(String env, String serviceCode) {
        RuntimeEnv runtimeEnv = runtimeEnvService.findByCode(env);
        if (Objects.isNull(runtimeEnv)) {
            return ResultData.fail("运行环境中未找到代码[" + env + "].");
        }
        RestTemplate restTemplate = new RestTemplate();
        //向restTemplate中添加自定义的拦截器
        restTemplate.getInterceptors().add(new TokenInterceptor());

        String baseUrl = runtimeEnv.getAgentServer();
        return restTemplate.getForObject(baseUrl + "/serviceInstance/getServiceInstance?serviceCode=" + serviceCode, ResultData.class);
    }
}
