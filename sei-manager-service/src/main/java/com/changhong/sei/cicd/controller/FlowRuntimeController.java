package com.changhong.sei.cicd.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.cicd.api.FlowRuntimeApi;
import com.changhong.sei.cicd.dto.ApplyType;
import com.changhong.sei.cicd.dto.FlowToDoTaskDto;
import com.changhong.sei.cicd.service.FlowToDoTaskService;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 流程运行时(FlowRuntime)控制类
 *
 * @author sei
 * @since 2020-12-15 18:09:43
 */
@RestController
@Api(value = "FlowRuntimeApi", tags = "流程运行时服务")
@RequestMapping(path = "flow/runtime", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlowRuntimeController implements FlowRuntimeApi {
    /**
     * 流程定义服务对象
     */
    @Autowired
    private FlowToDoTaskService toDoTaskService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 获取待办任务数
     *
     * @return 操作结果
     */
    @Override
    public ResultData<Map<ApplyType, Integer>> getTodoTaskNum() {
        return toDoTaskService.getTodoTaskNum(ContextUtil.getUserAccount());
    }

    /**
     * 获取待办任务
     *
     * @return 操作结果
     */
    @Override
    public ResultData<List<FlowToDoTaskDto>> getTodoTasks() {
        return toDoTaskService.getTodoTasks(ContextUtil.getUserAccount(), null);
    }

    /**
     * 获取待办任务
     *
     * @param type 申请类型
     * @return 操作结果
     * @see ApplyType
     */
    @Override
    public ResultData<List<FlowToDoTaskDto>> getTodoTasksByType(String type) {
        ApplyType applyType = EnumUtils.getEnum(ApplyType.class, type);
        return toDoTaskService.getTodoTasks(ContextUtil.getUserAccount(), applyType);
    }
}