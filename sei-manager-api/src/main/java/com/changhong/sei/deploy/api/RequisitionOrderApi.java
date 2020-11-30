package com.changhong.sei.deploy.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.ApplyType;
import com.changhong.sei.deploy.dto.FlowToDoTaskDto;
import com.changhong.sei.deploy.dto.TaskHandleRequest;
import com.changhong.sei.deploy.dto.TaskSubmitRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
     * 获取待办任务数
     *
     * @return 操作结果
     */
    @GetMapping(path = "getTodoTaskNum")
    @ApiOperation(value = "获取待办任务数", notes = "获取待办任务数")
    ResultData<Map<ApplyType, Integer>> getTodoTaskNum();

    /**
     * 获取待办任务
     *
     * @return 操作结果
     */
    @GetMapping(path = "getTodoTasks")
    @ApiOperation(value = "获取待办任务", notes = "获取待办任务")
    ResultData<List<FlowToDoTaskDto>> getTodoTasks();

    /**
     * 获取待办任务
     *
     * @param type 申请类型
     * @return 操作结果
     * @see ApplyType
     */
    @GetMapping(path = "{type}/getTodoTasks")
    @ApiImplicitParam(name = "type", value = "申请类型. APPLICATION,MODULE,VERSION,PUBLISH,DEPLOY", paramType = "path")
    @ApiOperation(value = "获取待办任务", notes = "获取待办任务")
    ResultData<List<FlowToDoTaskDto>> getTodoTasksByType(@PathVariable("type") String type);

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