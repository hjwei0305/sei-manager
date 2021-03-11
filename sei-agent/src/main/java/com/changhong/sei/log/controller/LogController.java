package com.changhong.sei.log.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.log.api.LogApi;
import com.changhong.sei.log.dto.LogDetailResponse;
import com.changhong.sei.log.dto.LogResponse;
import com.changhong.sei.log.dto.LogSearch;
import com.changhong.sei.log.service.LogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 实现功能: 日志API服务实现
 */
@RestController
@RefreshScope
@Api(value = "LogApi", tags = "日志API服务")
@RequestMapping(path = "log", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogController implements LogApi {
    @Autowired
    private LogService service;

    /**
     * 分页查询
     */
    @Override
    public ResultData<PageResult<LogResponse>> findByPage(@Valid LogSearch search) {
        return service.findByPage(search);
    }

    /**
     * 获取日志明细
     */
    @Override
    public ResultData<LogDetailResponse> detail(String serviceName, String id) {
        return service.detail(serviceName, id);
    }

    /**
     * 根据TraceId获取日志
     */
    @Override
    public ResultData<List<LogResponse>> findByTraceId(String serviceName, String traceId) {
        return service.findByTraceId(serviceName, traceId);
    }
}
