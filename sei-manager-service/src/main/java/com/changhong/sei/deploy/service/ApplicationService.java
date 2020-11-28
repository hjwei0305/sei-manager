package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.ApplicationDao;
import com.changhong.sei.deploy.dto.ApplicationType;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import com.changhong.sei.deploy.entity.AppModule;
import com.changhong.sei.deploy.entity.Application;
import com.changhong.sei.deploy.entity.RequisitionOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Objects;

/**
 * 应用服务(Application)业务逻辑实现类
 *
 * @author sei
 * @since 2020-10-30 15:20:57
 */
@Service("applicationService")
public class ApplicationService extends BaseEntityService<Application> {
    @Autowired
    private ApplicationDao dao;
    @Autowired
    private AppModuleService appModuleService;
    @Autowired
    private RequisitionOrderService requisitionOrderService;

    @Override
    protected BaseEntityDao<Application> getDao() {
        return dao;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查状态控制删除
        Application app = this.findOne(id);
        if (Objects.isNull(app)) {
            return OperateResult.operationFailure("[" + id + "]应用不存在,删除失败!");
        }
        if (!app.getFrozen()) {
            return OperateResult.operationFailure("[" + id + "]应用已审核确认,不允许删除!");
        }
        if (appModuleService.isExistsByProperty(AppModule.FIELD_APP_ID, id)) {
            return OperateResult.operationFailure("[" + id + "]应用存在应用模块,不允许删除!");
        }
        return super.preDelete(id);
    }

    /**
     * 创建应用申请单
     *
     * @param application 应用
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> createRequisition(Application application) {
        // 申请是设置为冻结状态,带申请审核确认后再值为可用状态
        application.setFrozen(Boolean.TRUE);
        // 保存应用
        OperateResultWithData<Application> resultWithData = this.save(application);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionRecord = new RequisitionOrder();
            // 申请类型:应用申请
            requisitionRecord.setApplicationType(ApplicationType.APPLICATION);
            // 应用id
            requisitionRecord.setRelationId(application.getId());
            // 申请摘要
            requisitionRecord.setSummary(application.getGroupName().concat("-")
                    .concat(application.getName())
                    .concat("[").concat(application.getCode()).concat("]"));

            ResultData<Void> result = requisitionOrderService.createRequisition(requisitionRecord);
            if (result.successful()) {
                return ResultData.success();
            } else {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail(result.getMessage());
            }
        } else {
            return ResultData.fail(resultWithData.getMessage());
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
        Application entity = this.findOne(application.getId());
        if (Objects.isNull(entity)) {
            return ResultData.fail("应用不存在!");
        }
        // 检查应用审核状态
        if (!entity.getFrozen()) {
            return ResultData.fail("应用已审核,不允许编辑!");
        }

        entity.setCode(application.getCode());
        entity.setName(application.getName());
        entity.setGroupCode(application.getGroupCode());
        entity.setGroupName(application.getGroupName());
        entity.setVersion(application.getVersion());
        entity.setRemark(application.getRemark());

        // 保存应用
        OperateResultWithData<Application> resultWithData = this.save(entity);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionRecord = requisitionOrderService.getByRelationId(entity.getId());
            if (Objects.isNull(requisitionRecord)) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail("申请单不存在!");
            }
            // 检查申请单是否已审核
            if (ApprovalStatus.initial != requisitionRecord.getApprovalStatus()) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail("申请单不存在!");
            }

            // 申请类型:应用申请
            requisitionRecord.setApplicationType(ApplicationType.APPLICATION);
            // 应用id
            requisitionRecord.setRelationId(entity.getId());
            // 申请摘要
            requisitionRecord.setSummary(entity.getGroupName().concat("-")
                    .concat(entity.getName())
                    .concat("[").concat(entity.getCode()).concat("]"));

            ResultData<Void> result = requisitionOrderService.createRequisition(requisitionRecord);
            if (result.successful()) {
                return ResultData.success();
            } else {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail(result.getMessage());
            }
        } else {
            return ResultData.fail(resultWithData.getMessage());
        }
    }

    /**
     * 创建应用申请单
     *
     * @param id@return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> deleteRequisition(String id) {
        Application application = this.findOne(id);
        if (Objects.nonNull(application)) {
            if (application.getFrozen()) {
                // 删除应用
                this.delete(id);

                // 删除申请单
                ResultData<Void> resultData = requisitionOrderService.deleteRequisition(id);
                if (resultData.successful()) {
                    return ResultData.success();
                } else {
                    // 事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultData.fail(resultData.getMessage());
                }
            } else {
                return ResultData.fail("应用已审核,禁止删除!");
            }
        }
        return ResultData.fail("应用不存在!");
    }
}
