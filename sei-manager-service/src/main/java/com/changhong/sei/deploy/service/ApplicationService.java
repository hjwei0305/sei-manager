package com.changhong.sei.deploy.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.ApplicationDao;
import com.changhong.sei.deploy.dto.ApplicationType;
import com.changhong.sei.deploy.entity.Application;
import com.changhong.sei.deploy.entity.RequisitionRecord;
import com.changhong.sei.util.IdGenerator;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private RequisitionRecordService requisitionRecordService;

    @Override
    protected BaseEntityDao<Application> getDao() {
        return dao;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        // 检查状态控制删除
        Application app = this.findOne(s);
        if (Objects.isNull(app)) {
            return OperateResult.operationFailure("[" + s + "]应用不存在,删除失败!");
        }
        if (!app.getFrozen()) {
            return OperateResult.operationFailure("[" + s + "]应用已审核确认,不允许删除!");
        }
        return super.preDelete(s);
    }

    /**
     * 创建应用申请单
     *
     * @param application 应用
     * @return 操作结果
     */
    public ResultData<Void> createRequisition(Application application) {
        // 申请是设置为冻结状态,带申请审核确认后再值为可用状态
        application.setFrozen(Boolean.TRUE);
        // 保存应用
        OperateResultWithData<Application> resultWithData = this.save(application);
        if (resultWithData.successful()) {
            RequisitionRecord requisitionRecord = new RequisitionRecord();
            // 申请类型:应用申请
            requisitionRecord.setApplicationType(ApplicationType.APPLICATION);
            // 应用id
            requisitionRecord.setRelationId(application.getId());
            // 任务号
            requisitionRecord.setTaskNo(String.valueOf(IdGenerator.nextId()));
            // 任务名称
            requisitionRecord.setTaskName("申请");
            SessionUser sessionUser = ContextUtil.getSessionUser();
            // 申请人账号
            requisitionRecord.setApplicantAccount(sessionUser.getAccount());
            requisitionRecord.setApplicantUserName(sessionUser.getUserName());
            requisitionRecord.setApplicationTime(LocalDateTime.now());
            // TODO 处理人
            requisitionRecord.setHandleAccount("20045203");
            requisitionRecord.setHandleUserName("马超");

            OperateResultWithData<RequisitionRecord> result = requisitionRecordService.save(requisitionRecord);
            if (result.successful()) {
                return ResultData.success();
            } else {
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
    public ResultData<Void> modifyRequisition(Application application) {
        return null;
    }

    /**
     * 创建应用申请单
     *
     * @param id@return 操作结果
     */
    public ResultData<Void> deleteRequisition(String id) {
        return null;
    }
}
