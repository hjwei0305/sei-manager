package com.changhong.sei.deploy.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.FlowTaskInstanceDao;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import com.changhong.sei.deploy.dto.OperationType;
import com.changhong.sei.deploy.entity.FlowPublished;
import com.changhong.sei.deploy.entity.FlowTaskInstance;
import com.changhong.sei.deploy.entity.RequisitionOrder;
import com.changhong.sei.manager.commom.EmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 流程任务实例(FlowTaskInstance)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Service("flowTaskInstanceService")
public class FlowTaskInstanceService extends BaseEntityService<FlowTaskInstance> {
    @Autowired
    private FlowTaskInstanceDao dao;
    @Autowired
    private FlowPublishedService publishedService;
    @Autowired
    private FlowTaskHistoryService historyService;
    @Autowired
    private EmailManager emailManager;

    @Override
    protected BaseEntityDao<FlowTaskInstance> getDao() {
        return dao;
    }

    /**
     * 提交申请单
     *
     * @param requisition 业务实体DTO
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<RequisitionOrder> submit(String flowTypeId, String flowTypeName, RequisitionOrder requisition) {
        // 获取申请单
        if (Objects.isNull(requisition)) {
            return ResultData.fail("申请单不存在!");
        }

        if (ApprovalStatus.INITIAL != requisition.getApprovalStatus()) {
            return ResultData.fail("申请单当前状态[" + requisition.getApprovalStatus() + "], 不是初始状态");
        }

        // 通过流程类型获取最新的流程实例版本
        Long version = publishedService.getLatestVersion(flowTypeId);

        // 通过流程类型,实例版本及任务号,获取下一个任务
        ResultData<FlowPublished> resultData = publishedService.getNextTaskAndCheckLast(flowTypeId, version, Integer.MIN_VALUE);
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }

        FlowPublished nextTask = resultData.getData();
        if (Objects.isNull(nextTask)) {
            // 提交流程,不应该存在没有下个任务的情况
            return ResultData.fail("流程任务配置错误.");
        }

        SessionUser sessionUser = ContextUtil.getSessionUser();
        // 存在下个任务,则创建下个任务的待办任务
        ResultData<FlowTaskInstance> result = this.createToDoTask(requisition, sessionUser, nextTask);
        if (result.successful()) {
            String msg = "提交任务";
            // 记录任务执行历史
            ResultData<Void> recordResult = historyService.record(result.getData(), OperationType.SUBMIT, sessionUser.getAccount(), sessionUser.getUserName(), msg);
            if (recordResult.successful()) {
                // 指定流程类型
                requisition.setFlowTypeId(flowTypeId);
                requisition.setFlowTypeName(flowTypeName);
                // 记录流程版本
                requisition.setFlowVersion(version);
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
        dao.deleteByOrderId(orderId);
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
        FlowTaskInstance currentTask = this.findOne(taskId);
        if (Objects.isNull(currentTask)) {
            return ResultData.fail("任务不存在!");
        }

        if (!currentTask.getPending()) {
            return ResultData.fail("任务已被他人处理!");
        }
        // 当前任务待办状态更新为已处理
        currentTask.setPending(Boolean.FALSE);
        OperateResultWithData<FlowTaskInstance> updateResult = this.save(currentTask);
        if (updateResult.notSuccessful()) {
            LogUtil.error("任务待办状态更新失败: " + updateResult.getMessage());
            return ResultData.fail("任务待办状态更新失败!");
        }

        // 通过流程类型,实例版本及任务号,获取下一个任务
        ResultData<FlowPublished> resultData = publishedService.getNextTaskAndCheckLast(requisition.getFlowTypeId(), requisition.getFlowVersion(), currentTask.getTaskNo());
        if (resultData.failed()) {
            LogUtil.error(resultData.getMessage());
            return ResultData.fail("不存在下个任务!");
        }

        SessionUser sessionUser = ContextUtil.getSessionUser();
        // 如果通过类型及版本找到有对应的任务,但通过任务号没有匹配上,则认为当前任务是最后一个任务,流程应结束
        FlowPublished nextTask = resultData.getData();
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
            ResultData<FlowTaskInstance> result = this.createToDoTask(requisition, sessionUser, nextTask);
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
        FlowTaskInstance currentTask = this.findOne(taskId);
        if (Objects.isNull(currentTask)) {
            return ResultData.fail("任务不存在!");
        }

        if (!currentTask.getPending()) {
            return ResultData.fail("任务已被他人处理!");
        }
        // 当前任务待办状态更新为已处理
        currentTask.setPending(Boolean.FALSE);
        OperateResultWithData<FlowTaskInstance> updateResult = this.save(currentTask);
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
        FlowTaskInstance task = new FlowTaskInstance();
        // 申请单id
        task.setOrderId(requisition.getId());
        // 业务关联id
        task.setRelationId(requisition.getRelationId());
        // 申请类型
        task.setApplicationType(requisition.getApplicationType());
        // 发起人
        task.setInitiatorAccount(sessionUser.getAccount());
        task.setInitiatorUserName(sessionUser.getUserName());
        // 发起时间
        task.setInitTime(LocalDateTime.now());

        // 通过流程类型,实例版本及任务号,获取下一个任务
        ResultData<FlowPublished> resultData = publishedService.getNextTaskAndCheckLast(requisition.getFlowTypeId(), requisition.getFlowVersion(), Integer.MIN_VALUE);
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }

        // 任务
        FlowPublished preTask = resultData.getData();
        if (Objects.nonNull(preTask)) {
            task.setTaskNo(preTask.getRank());
            task.setTaskName(preTask.getTaskName());
        }

        // 记录任务执行历史
        ResultData<Void> recordResult = historyService.record(task, OperationType.CANCEL, sessionUser.getAccount(), sessionUser.getUserName(), message);
        if (recordResult.failed()) {
            return ResultData.fail(recordResult.getMessage());
        } else {
            // 更新所有任务为已处理
            List<FlowTaskInstance> tasks = this.findListByProperty(FlowTaskInstance.FIELD_ORDER_ID, requisition.getId());
            for (FlowTaskInstance taskInstance : tasks) {
                taskInstance.setPending(Boolean.FALSE);
            }
            this.save(tasks);
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
    private ResultData<FlowTaskInstance> createToDoTask(RequisitionOrder requisition, SessionUser sessionUser, FlowPublished task) {
        // 存在下个任务,则创建下个任务的待办任务
        FlowTaskInstance taskInstance = new FlowTaskInstance();
        // 申请单id
        taskInstance.setOrderId(requisition.getId());
        // 业务关联id
        taskInstance.setRelationId(requisition.getRelationId());
        // 申请类型
        taskInstance.setApplicationType(requisition.getApplicationType());
        // 发起人
        taskInstance.setInitiatorAccount(sessionUser.getAccount());
        taskInstance.setInitiatorUserName(sessionUser.getUserName());
        // 发起时间
        taskInstance.setInitTime(LocalDateTime.now());
        // 任务
        taskInstance.setFlowTypeId(task.getTypeId());
        taskInstance.setFlowTypeName(task.getTypeName());
        // 任务
        taskInstance.setTaskNo(task.getRank());
        taskInstance.setTaskName(task.getTaskName());
        // 处理人
        taskInstance.setExecuteAccount(task.getHandleAccount());
        taskInstance.setExecuteUserName(task.getHandleUserName());

        // 保存
        OperateResultWithData<FlowTaskInstance> result = this.save(taskInstance);
        if (result.successful()) {
            emailManager.sendMail(task.getTypeName() + " - " + task.getTaskName(), requisition.getSummary(), sessionUser.getAccount());
            return ResultData.success(result.getData());
        } else {
            return ResultData.fail(result.getMessage());
        }
    }
}