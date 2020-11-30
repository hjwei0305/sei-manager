package com.changhong.sei.deploy.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.TaskHandleRequest;
import com.changhong.sei.deploy.dto.TaskSubmitRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 申请记录(RequisitionOrder)API
 *
 * @author sei
 * @since 2020-11-26 14:45:24
 */
@Valid
@FeignClient(name = "sei-manager", path = "requisition")
public interface RequisitionOrderApi {

    /**
     * 提交申请单
     *
     * @param submitRequest 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "submit", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "提交申请单", notes = "提交申请单")
    ResultData<Void> submit(@RequestBody @Valid TaskSubmitRequest submitRequest);

    /**
     * 申请单待办任务处理
     *
     * @param handleRequest 任务处理请求
     * @return 操作结果
     */
    @PostMapping(path = "handle", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "处理申请单待办任务", notes = "处理申请单待办任务")
    ResultData<Void> handle(@RequestBody @Valid TaskHandleRequest handleRequest);
}