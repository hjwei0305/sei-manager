package com.changhong.sei.log.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.log.dto.LogDetailResponse;
import com.changhong.sei.log.dto.LogResponse;
import com.changhong.sei.log.dto.LogSearch;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 实现功能： 日志API
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-27 13:29
 */
@FeignClient(name = "sei-manager", path = "log")
public interface LogApi {
    /**
     * 分页查询
     */
    @PostMapping(value = "findByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "分页查询", notes = "分页查询")
    ResultData<PageResult<LogResponse>> findByPage(@RequestBody @Valid LogSearch search);

    /**
     * 获取日志明细
     */
    @GetMapping(value = "detail")
    @ApiOperation(value = "日志明细", notes = "获取日志明细")
    ResultData<LogDetailResponse> detail(@RequestParam("serviceName") String serviceName, @RequestParam("id") String id);

    /**
     * 根据TraceId获取日志
     */
    @GetMapping(value = "findByTraceId")
    @ApiOperation(value = "根据TraceId获取日志", notes = "根据TraceId获取日志")
    ResultData<List<LogResponse>> findByTraceId(@RequestParam("serviceName") String serviceName, @RequestParam("traceId") String traceId);

}
