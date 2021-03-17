package com.changhong.sei.cicd.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.cicd.dto.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 流程运行时(FlowRuntime)API
 *
 * @author sei
 * @since 2020-12-15 18:09:47
 */
@Valid
@FeignClient(name = "sei-manager", path = "flow/runtime")
public interface FlowRuntimeApi {

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

}