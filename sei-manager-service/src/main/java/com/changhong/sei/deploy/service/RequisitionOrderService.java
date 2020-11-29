package com.changhong.sei.deploy.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.RequisitionOrderDao;
import com.changhong.sei.deploy.dto.ApprovalCancelRequest;
import com.changhong.sei.deploy.dto.ApprovalRejectRequest;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import com.changhong.sei.deploy.dto.ApprovalSubmitRequest;
import com.changhong.sei.deploy.entity.FlowTaskInstance;
import com.changhong.sei.deploy.entity.FlowPublished;
import com.changhong.sei.deploy.entity.RequisitionOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * 申请记录(RequisitionRecord)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Service("requisitionOrderService")
public class RequisitionOrderService extends BaseEntityService<RequisitionOrder> {
    @Autowired
    private RequisitionOrderDao dao;
    @Autowired
    private FlowTaskInstanceService flowTaskInstanceService;

    @Override
    protected BaseEntityDao<RequisitionOrder> getDao() {
        return dao;
    }

    /**
     * 根据关联id获取申请单
     *
     * @param relationId 关联id
     * @return 申请单
     */
    public RequisitionOrder getByRelationId(String relationId) {
        return this.findFirstByProperty(RequisitionOrder.FIELD_RELATION_ID, relationId);
    }

    /**
     * 创建申请单
     *
     * @param requisitionRecord 申请单
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> createRequisition(RequisitionOrder requisitionRecord) {
        // 审核状态:初始
        requisitionRecord.setApprovalStatus(ApprovalStatus.initial);
        SessionUser sessionUser = ContextUtil.getSessionUser();
        // 申请人账号
        requisitionRecord.setApplicantAccount(sessionUser.getAccount());
        requisitionRecord.setApplicantUserName(sessionUser.getUserName());
        requisitionRecord.setApplicationTime(LocalDateTime.now());

        OperateResultWithData<RequisitionOrder> result = this.save(requisitionRecord);
        if (result.successful()) {
            return ResultData.success();
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 修改申请单
     *
     * @param requisitionRecord 申请单
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> modifyRequisition(RequisitionOrder requisitionRecord) {
        OperateResultWithData<RequisitionOrder> result = this.save(requisitionRecord);
        if (result.successful()) {
            return ResultData.success();
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 删除申请单
     *
     * @param relationId 关联id
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> deleteRequisition(String relationId) {
        RequisitionOrder requisition = this.findFirstByProperty(RequisitionOrder.FIELD_RELATION_ID, relationId);
        if (Objects.nonNull(requisition)) {
            if (ApprovalStatus.initial == requisition.getApprovalStatus()) {
                OperateResult result = this.delete(requisition.getId());
                if (result.successful()) {
                    return ResultData.success();
                } else {
                    return ResultData.fail(result.getMessage());
                }
            } else {
                return ResultData.fail("申请单审核状态非初始状态,禁止删除!");
            }
        }
        return ResultData.fail("申请单不存在!");
    }

    /**
     * 提交申请单
     *
     * @param submitRequest 业务实体DTO
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> submit(@Valid ApprovalSubmitRequest submitRequest) {
        // 获取申请单
        RequisitionOrder requisition = this.findOne(submitRequest.getRequisitionId());
        if (Objects.isNull(requisition)) {
            return ResultData.fail("申请单不存在!");
        }

        ResultData<RequisitionOrder> result = flowTaskInstanceService.submit(submitRequest.getFlowTypeId(), submitRequest.getFlowTypeName(), requisition);
        if (result.successful()) {
            OperateResultWithData<RequisitionOrder> resultWithData = this.save(requisition);
            if (resultWithData.successful()) {
                return ResultData.success();
            } else {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail(resultWithData.getMessage());
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