package com.changhong.sei.deploy.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.FlowTaskInstanceDao;
import com.changhong.sei.deploy.dto.ApprovalCancelRequest;
import com.changhong.sei.deploy.dto.ApprovalRejectRequest;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import com.changhong.sei.deploy.entity.FlowPublished;
import com.changhong.sei.deploy.entity.FlowTaskInstance;
import com.changhong.sei.deploy.entity.RequisitionOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
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

        // 通过流程类型获取最新的流程实例版本
        Long version = publishedService.getLatestVersion(flowTypeId);

        // 通过流程类型,实例版本及任务号,获取下一个任务
        FlowPublished nextTask = publishedService.getNextTask(flowTypeId, version, 0);

        FlowTaskInstance taskInstance = new FlowTaskInstance();
        // 申请单id
        taskInstance.setOrderId(requisition.getId());
        // 业务关联id
        taskInstance.setRelationId(requisition.getRelationId());
        // 申请类型
        taskInstance.setApplicationType(requisition.getApplicationType());
        // 发起人
        SessionUser sessionUser = ContextUtil.getSessionUser();
        taskInstance.setInitiatorAccount(sessionUser.getAccount());
        taskInstance.setInitiatorUserName(sessionUser.getUserName());
        // 发起时间
        taskInstance.setInitTime(LocalDateTime.now());
        // 任务
        taskInstance.setTaskNo(nextTask.getRank());
        taskInstance.setTaskName(nextTask.getTaskName());
        // 处理人
        taskInstance.setExecuteAccount(nextTask.getHandleAccount());
        taskInstance.setExecuteUserName(nextTask.getHandleUserName());

        // 保存
        OperateResultWithData<FlowTaskInstance> result = this.save(taskInstance);
        if (result.successful()) {
            String msg = "提交任务";
            // 记录任务执行历史
            ResultData<Void> recordResult = historyService.record(taskInstance, sessionUser.getAccount(), sessionUser.getUserName(), msg);
            if (recordResult.successful()) {
                // 指定流程类型
                requisition.setFlowTypeId(flowTypeId);
                requisition.setFlowTypeName(flowTypeName);
                // 记录流程版本
                requisition.setFlowVersion(version);
                // 更新申请单状态为审核中
                requisition.setApprovalStatus(ApprovalStatus.processing);

                return ResultData.success(requisition);
            } else {
                return ResultData.fail(recordResult.getMessage());
            }
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 驳回申请单
     *
     * @param rejectRequest 驳回请求
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> reject(@Valid ApprovalRejectRequest rejectRequest) {
        return ResultData.success();
    }

    /**
     * 取消(终止)申请单
     *
     * @param cancelRequest 取消(终止)请求
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> cancel(@Valid ApprovalCancelRequest cancelRequest) {
        return ResultData.success();
    }
}