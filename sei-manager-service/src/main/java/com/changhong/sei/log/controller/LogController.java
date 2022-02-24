package com.changhong.sei.log.controller;

import com.changhong.sei.common.TokenInterceptor;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.ge.entity.RuntimeEnv;
import com.changhong.sei.ge.service.RuntimeEnvService;
import com.changhong.sei.log.api.LogApi;
import com.changhong.sei.log.dto.LogDetailResponse;
import com.changhong.sei.log.dto.LogResponse;
import com.changhong.sei.log.dto.LogSearchRequest;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 实现功能: 日志API服务实现
 */
@RestController
@RefreshScope
@Api(value = "LogApi", tags = "日志API服务")
@RequestMapping(path = "log", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogController implements LogApi {
    @Autowired
    private RuntimeEnvService runtimeEnvService;
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 分页查询
     */
    @Override
    public ResultData<PageResult<LogResponse>> findByPage(@Valid LogSearchRequest search) {
        RuntimeEnv runtimeEnv = runtimeEnvService.findByCode(search.getEnv());
        if (Objects.isNull(runtimeEnv)) {
            return ResultData.fail("运行环境中未找到代码[" + search.getEnv() + "].");
        }
        // RestTemplate restTemplate = new RestTemplate();
        //向restTemplate中添加自定义的拦截器
        restTemplate.getInterceptors().add(tokenInterceptor);

        String baseUrl = runtimeEnv.getAgentServer();
        return restTemplate.postForObject(baseUrl + "/log/findByPage", search, ResultData.class);
    }

    /**
     * 获取日志明细
     */
    @Override
    public ResultData<LogDetailResponse> detail(String env, String serviceName, String id) {
        RuntimeEnv runtimeEnv = runtimeEnvService.findByCode(env);
        if (Objects.isNull(runtimeEnv)) {
            return ResultData.fail("运行环境中未找到代码[" + env + "].");
        }
        // RestTemplate restTemplate = new RestTemplate();
        //向restTemplate中添加自定义的拦截器
        restTemplate.getInterceptors().add(tokenInterceptor);
        Map<String, String> params = new HashMap<>();
        params.put("serviceName", serviceName);
        params.put("id", id);

        String baseUrl = runtimeEnv.getAgentServer();
        return restTemplate.getForObject(baseUrl + "/log/detail", ResultData.class, params);
    }

    /**
     * 根据TraceId获取日志
     */
    @Override
    public ResultData<List<LogResponse>> findByTraceId(String env, String serviceName, String traceId) {
        RuntimeEnv runtimeEnv = runtimeEnvService.findByCode(env);
        if (Objects.isNull(runtimeEnv)) {
            return ResultData.fail("运行环境中未找到代码[" + env + "].");
        }
        // RestTemplate restTemplate = new RestTemplate();
        //向restTemplate中添加自定义的拦截器
        restTemplate.getInterceptors().add(tokenInterceptor);
        Map<String, String> params = new HashMap<>();
        params.put("serviceName", serviceName);
        params.put("traceId", traceId);

        String baseUrl = runtimeEnv.getAgentServer();
        return restTemplate.getForObject(baseUrl + "/log/findByTraceId", ResultData.class, params);
    }
}
