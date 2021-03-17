package com.changhong.sei.cicd.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.cicd.api.RequisitionOrderApi;
import com.changhong.sei.cicd.dto.ApplyType;
import com.changhong.sei.cicd.dto.FlowToDoTaskDto;
import com.changhong.sei.cicd.dto.TaskHandleRequest;
import com.changhong.sei.cicd.dto.TaskSubmitRequest;
import com.changhong.sei.cicd.service.FlowToDoTaskService;
import com.changhong.sei.cicd.service.RequisitionOrderService;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 申请记录(RequisitionRecord)控制类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@RestController
@Api(value = "RequisitionRecordApi", tags = "申请审核服务")
@RequestMapping(path = "requisition", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequisitionOrderController implements RequisitionOrderApi {
    /**
     * 申请记录服务对象
     */
    @Autowired
    private RequisitionOrderService service;
    @Autowired
    private FlowToDoTaskService flowToDoTaskService;

    /**
     * 获取待办任务数
     *
     * @return 操作结果
     */
    @Override
    public ResultData<Map<ApplyType, Integer>> getTodoTaskNum() {
        return flowToDoTaskService.getTodoTaskNum(ContextUtil.getUserAccount());
    }

    /**
     * 获取待办任务
     *
     * @return 操作结果
     */
    @Override
    public ResultData<List<FlowToDoTaskDto>> getTodoTasks() {
        return flowToDoTaskService.getTodoTasks(ContextUtil.getUserAccount(), null);
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
        return flowToDoTaskService.getTodoTasks(ContextUtil.getUserAccount(), applyType);
    }

    /**
     * 提交申请单
     *
     * @param submitRequest 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> submit(@Valid TaskSubmitRequest submitRequest) {
        return service.submit(submitRequest);
    }

    /**
     * 申请单待办任务处理
     *
     * @param handleRequest 任务处理请求
     * @return 操作结果
     */
    @Override
    public ResultData<Void> handle(@Valid TaskHandleRequest handleRequest) {
        return service.handleTask(handleRequest);
    }
}