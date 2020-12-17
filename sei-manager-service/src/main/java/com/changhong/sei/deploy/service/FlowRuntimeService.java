package com.changhong.sei.deploy.service;

import com.changhong.sei.common.ThymeLeafHelper;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dto.ApplyType;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import com.changhong.sei.deploy.dto.OperationType;
import com.changhong.sei.deploy.entity.*;
import com.changhong.sei.manager.commom.EmailManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-17 00:02
 */
@Service("flowRuntimeService")
public class FlowRuntimeService {
    private static final Logger LOG = LoggerFactory.getLogger(FlowRuntimeService.class);

    @Autowired
    private FlowTypeService typeService;
    @Autowired
    private FlowInstanceService instanceService;
    @Autowired
    private FlowInstanceTaskService instanceTaskService;
    @Autowired
    private FlowToDoTaskService toDoTaskService;
    @Autowired
    private FlowTaskHistoryService historyService;
    @Autowired
    private EmailManager emailManager;

    @Value("${sei.server.web}")
    private String serverWeb;
    @Value("${sei.application.name:SEI开发运维平台}")
    private String managerName;


    /**
     * 提交申请单
     *
     * @param requisition 业务实体DTO
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<RequisitionOrder> submit(String bizKey, RequisitionOrder requisition) {
        // 获取申请单
        if (Objects.isNull(requisition)) {
            return ResultData.fail("申请单不存在!");
        }

        if (ApprovalStatus.INITIAL != requisition.getApprovalStatus()) {
            return ResultData.fail("申请单当前状态[" + requisition.getApprovalStatus() + "], 不是初始状态");
        }
        // 申请类型枚举
        ApplyType applyType = requisition.getApplyType();
        // 流程类型代码
        String flowTypeCode = applyType.name();
        // 流程类型
        FlowType flowType = typeService.getFlowType(flowTypeCode);
        if (Objects.isNull(flowType)) {
            return ResultData.fail("未找到流程类型[" + flowTypeCode + "].");
        }
        // 当前流程版本
        int version = flowType.getVersion();

        // 查找流程实例
        ResultData<FlowInstance> resultData = instanceService.getFlowInstance(flowTypeCode, version, bizKey);
        if (resultData.failed()) {
            return ResultData.fail("未找到流程类型[" + flowTypeCode + "]版本[" + version + "]的流程实例.");
        }
        FlowInstance flowInstance = resultData.getData();
        // 指定流程类型
        requisition.setFlowInstanceId(flowInstance.getId());
        requisition.setFlowTypeName(flowInstance.getName());
        // 记录流程版本
        requisition.setFlowVersion(version);

        // 通过流程类型,实例版本及任务号,获取下一个任务
        ResultData<FlowInstanceTask> taskResult = instanceTaskService.getNextTaskAndCheckLast(flowInstance.getId(), Integer.MIN_VALUE);
        if (taskResult.failed()) {
            return ResultData.fail(taskResult.getMessage());
        }
        FlowInstanceTask nextTaskNode = taskResult.getData();
        if (Objects.isNull(nextTaskNode)) {
            // 提交流程,不应该存在没有下个任务的情况
            return ResultData.fail("流程任务配置错误.");
        }

        SessionUser sessionUser = ContextUtil.getSessionUser();
        // 存在下个任务,则创建下个任务的待办任务
        ResultData<FlowToDoTask> result = this.createToDoTask(requisition, sessionUser, nextTaskNode);
        if (result.successful()) {
            String msg = "提交任务";
            // 记录任务执行历史
            ResultData<Void> recordResult = historyService.record(result.getData(), OperationType.SUBMIT, sessionUser.getAccount(), sessionUser.getUserName(), msg);
            if (recordResult.successful()) {
                // 更新申请单状态为审核中
                requisition.setApprovalStatus(ApprovalStatus.PROCESSING);

                return ResultData.success(requisition);
            } else {
                return ResultData.fail(recordResult.getMessage());
            }
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 审核通过申请单
     *
     * @param requisition 申请单
     * @param message     处理消息
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<RequisitionOrder> handleTask(RequisitionOrder requisition, OperationType operationType, String taskInstanceId, String message) {
        // 获取申请单
        if (Objects.isNull(requisition)) {
            return ResultData.fail("申请单不存在!");
        }

        ResultData<RequisitionOrder> result;
        // 操作类型
        switch (operationType) {
            case PASSED:
                if (ApprovalStatus.PROCESSING != requisition.getApprovalStatus()) {
                    return ResultData.fail("申请单当前状态[" + requisition.getApprovalStatus() + "], 不在审核中");
                }
                // 审核通过
                result = this.passed(requisition, taskInstanceId, message);
                break;
            case REJECT:
                if (ApprovalStatus.PROCESSING != requisition.getApprovalStatus()) {
                    return ResultData.fail("申请单当前状态[" + requisition.getApprovalStatus() + "], 不在审核中");
                }
                // 驳回
                result = this.reject(requisition, taskInstanceId, message);
                break;
            case CANCEL:
                if (ApprovalStatus.PASSED == requisition.getApprovalStatus()) {
                    return ResultData.fail("申请单当前状态[" + requisition.getApprovalStatus() + "], 不允许撤销");
                }
                // 取消
                result = this.cancel(requisition, message);
                break;
            default:
                result = ResultData.fail("任务处理类型错误.");
        }

        return result;
    }

    /**
     * 按申请单id删除任务
     *
     * @param orderId 申请单id
     * @return 操作状态
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTaskByOrderId(String orderId) {
        // 删除任务历史
        historyService.deleteByOrderId(orderId);
        // 删除待办任务
        toDoTaskService.deleteByOrderId(orderId);
    }

    /**
     * 审核通过申请单
     *
     * @param requisition 申请单
     * @param taskId      当前任务id
     * @param message     处理消息
     * @return 操作结果
     */
    private ResultData<RequisitionOrder> passed(RequisitionOrder requisition, String taskId, String message) {
        // 获取当前任务
        FlowToDoTask currentTask = toDoTaskService.findOne(taskId);
        if (Objects.isNull(currentTask)) {
            return ResultData.fail("任务不存在!");
        }

        if (!currentTask.getPending()) {
            return ResultData.fail("任务已被他人处理!");
        }
        // 当前任务待办状态更新为已处理
        currentTask.setPending(Boolean.FALSE);
        OperateResultWithData<FlowToDoTask> updateResult = toDoTaskService.save(currentTask);
        if (updateResult.notSuccessful()) {
            LogUtil.error("任务待办状态更新失败: " + updateResult.getMessage());
            return ResultData.fail("任务待办状态更新失败!");
        }

        // 流程实例id
        String flowInstanceId = requisition.getFlowInstanceId();
        // 通过流程类型,实例版本及任务号,获取下一个任务
        ResultData<FlowInstanceTask> resultData = instanceTaskService.getNextTaskAndCheckLast(flowInstanceId, currentTask.getTaskNo());
        if (resultData.failed()) {
            LogUtil.error(resultData.getMessage());
            return ResultData.fail("不存在下个任务!");
        }

        SessionUser sessionUser = ContextUtil.getSessionUser();
        // 如果通过类型及版本找到有对应的任务,但通过任务号没有匹配上,则认为当前任务是最后一个任务,流程应结束
        FlowInstanceTask nextTask = resultData.getData();
        if (Objects.isNull(nextTask)) {
            // 记录任务执行历史
            ResultData<Void> recordResult = historyService.record(currentTask, OperationType.PASSED, sessionUser.getAccount(), sessionUser.getUserName(), message);
            if (recordResult.failed()) {
                return ResultData.fail(recordResult.getMessage());
            } else {
                // 更新申请单状态为通过
                requisition.setApprovalStatus(ApprovalStatus.PASSED);
                return ResultData.success(requisition);
            }
        } else {
            // 存在下个任务,则创建下个任务的待办任务
            ResultData<FlowToDoTask> result = this.createToDoTask(requisition, sessionUser, nextTask);
            if (result.successful()) {
                // 记录任务执行历史
                ResultData<Void> recordResult = historyService.record(currentTask, OperationType.PASSED, sessionUser.getAccount(), sessionUser.getUserName(), message);
                if (recordResult.failed()) {
                    return ResultData.fail(recordResult.getMessage());
                } else {
                    // 更新申请单状态为通过
                    requisition.setApprovalStatus(ApprovalStatus.PROCESSING);
                    return ResultData.success(requisition);
                }
            } else {
                return ResultData.fail(result.getMessage());
            }
        }
    }

    /**
     * 审核通过申请单
     *
     * @param requisition 申请单
     * @param taskId      当前任务id
     * @param message     处理消息
     * @return 操作结果
     */
    private ResultData<RequisitionOrder> reject(RequisitionOrder requisition, String taskId, String message) {
        // 获取当前任务
        FlowToDoTask currentTask = toDoTaskService.findOne(taskId);
        if (Objects.isNull(currentTask)) {
            return ResultData.fail("任务不存在!");
        }

        if (!currentTask.getPending()) {
            return ResultData.fail("任务已被他人处理!");
        }
        // 当前任务待办状态更新为已处理
        currentTask.setPending(Boolean.FALSE);
        OperateResultWithData<FlowToDoTask> updateResult = toDoTaskService.save(currentTask);
        if (updateResult.notSuccessful()) {
            LogUtil.error("任务待办状态更新失败: " + updateResult.getMessage());
            return ResultData.fail("任务待办状态更新失败!");
        }

        SessionUser sessionUser = ContextUtil.getSessionUser();
        // 记录任务执行历史
        ResultData<Void> recordResult = historyService.record(currentTask, OperationType.REJECT, sessionUser.getAccount(), sessionUser.getUserName(), message);
        if (recordResult.failed()) {
            return ResultData.fail(recordResult.getMessage());
        } else {
            // 驳回申请单状态: 未通过
            requisition.setApprovalStatus(ApprovalStatus.UNPASSED);
            return ResultData.success(requisition);
        }
    }

    /**
     * 审核通过申请单
     *
     * @param requisition 申请单
     * @param message     处理消息
     * @return 操作结果
     */
    private ResultData<RequisitionOrder> cancel(RequisitionOrder requisition, String message) {
        SessionUser sessionUser = ContextUtil.getSessionUser();
        FlowToDoTask task = new FlowToDoTask();
        // 申请单id
        task.setOrderId(requisition.getId());
        // 业务关联id
        task.setRelationId(requisition.getRelationId());
        // 申请类型
        task.setApplyType(requisition.getApplyType());
        // 发起人
        task.setInitiatorAccount(sessionUser.getAccount());
        task.setInitiatorUserName(sessionUser.getUserName());
        // 发起时间
        task.setInitTime(LocalDateTime.now());

        // 通过流程类型,实例版本及任务号,获取下一个任务
        ResultData<FlowInstanceTask> resultData = instanceTaskService.getNextTaskAndCheckLast(requisition.getFlowInstanceId(), Integer.MIN_VALUE);
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }

        // 任务
        FlowInstanceTask preTask = resultData.getData();
        if (Objects.nonNull(preTask)) {
            task.setTaskNo(preTask.getRank());
            task.setTaskName(preTask.getName());
        }

        // 记录任务执行历史
        ResultData<Void> recordResult = historyService.record(task, OperationType.CANCEL, sessionUser.getAccount(), sessionUser.getUserName(), message);
        if (recordResult.failed()) {
            return ResultData.fail(recordResult.getMessage());
        } else {
            // 更新所有任务为已处理
            toDoTaskService.updateStatus(Boolean.FALSE, requisition.getId());
            // 取消申请单状态: 初始
            requisition.setApprovalStatus(ApprovalStatus.INITIAL);
            return ResultData.success(requisition);
        }
    }

    /**
     * 创建待办任务
     *
     * @param requisition 申请单
     * @param sessionUser 当前用户
     * @param task        任务
     * @return 创建结果
     */
    private ResultData<FlowToDoTask> createToDoTask(RequisitionOrder requisition, SessionUser sessionUser, FlowInstanceTask task) {
        // 存在下个任务,则创建下个任务的待办任务
        FlowToDoTask toDoTask = new FlowToDoTask();
        // 任务
        toDoTask.setFlowTypeCode(requisition.getApplyType().name());
        toDoTask.setFlowTypeName(requisition.getFlowTypeName());
        // 任务
        toDoTask.setTaskNo(task.getRank());
        toDoTask.setTaskName(task.getName());
        // 发起人
        toDoTask.setInitiatorAccount(sessionUser.getAccount());
        toDoTask.setInitiatorUserName(sessionUser.getUserName());
        // 发起时间
        toDoTask.setInitTime(LocalDateTime.now());
        // 处理人
        toDoTask.setExecuteAccount(task.getHandleAccount());
        toDoTask.setExecuteUserName(task.getHandleUserName());

        // 申请单id
        toDoTask.setOrderId(requisition.getId());
        // 业务关联id
        toDoTask.setRelationId(requisition.getRelationId());
        // 申请类型
        toDoTask.setApplyType(requisition.getApplyType());
        toDoTask.setSummary(requisition.getSummary());

        // 保存
        toDoTaskService.save(toDoTask);
        try {
            Context context = new Context();
            context.setVariable("userName", task.getHandleUserName());
            context.setVariable("flowType", requisition.getFlowTypeName());
            context.setVariable("taskName", task.getName());
            context.setVariable("summary", requisition.getSummary());
            context.setVariable("url", serverWeb);
            context.setVariable("sysName", managerName);
            String content = ThymeLeafHelper.getTemplateEngine().process("notify/FlowTask.html", context);
            emailManager.sendMail(managerName + "-待办事项", content, sessionUser.getAccount());
        } catch (Exception e) {
            LogUtil.error("流程待办任务通知异常", e);
        }
        return ResultData.success(toDoTask);
    }
}
