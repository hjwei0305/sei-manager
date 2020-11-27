package com.changhong.sei.deploy.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.RequisitionOrderDao;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import com.changhong.sei.deploy.entity.Application;
import com.changhong.sei.deploy.entity.ApprovalRecord;
import com.changhong.sei.deploy.entity.RequisitionOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


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

    @Override
    protected BaseEntityDao<RequisitionOrder> getDao() {
        return dao;
    }


    /**
     * 创建申请单
     *
     * @param requisitionRecord 申请单
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> createRequisition(RequisitionOrder requisitionRecord) {
        // 任务号
//            requisitionRecord.setTaskNo(String.valueOf(IdGenerator.nextId()));
        // 任务名称
//            requisitionRecord.setTaskName("申请");
        SessionUser sessionUser = ContextUtil.getSessionUser();
        // 申请人账号
        requisitionRecord.setApplicantAccount(sessionUser.getAccount());
        requisitionRecord.setApplicantUserName(sessionUser.getUserName());
        requisitionRecord.setApplicationTime(LocalDateTime.now());
        // 审核状态:初始
        requisitionRecord.setApprovalStatus(ApprovalStatus.initial);
        // TODO 处理人
//            requisitionRecord.setHandleAccount("20045203");
//            requisitionRecord.setHandleUserName("马超");

        OperateResultWithData<RequisitionOrder> result = this.save(requisitionRecord);
        if (result.successful()) {
            ApprovalRecord record = new ApprovalRecord();
            record.setOrderId(requisitionRecord.getId());
            record.setRelationId(requisitionRecord.getRelationId());
            record.setApplicationType(requisitionRecord.getApplicationType());
            record.setTaskNo("");
            record.setTaskName("");
            // TODO 处理人
            record.setHandleAccount("20045203");
            record.setHandleUserName("马超");

            return ResultData.success();
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 创建应用申请单
     *
     * @param application 应用
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> modifyRequisition(Application application) {
        return null;
    }

    /**
     * 创建应用申请单
     *
     * @param id@return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> deleteRequisition(String id) {
        return null;
    }
}