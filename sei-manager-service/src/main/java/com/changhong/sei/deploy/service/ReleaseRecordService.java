package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.ReleaseRecordDao;
import com.changhong.sei.deploy.dao.ReleaseRecordRequisitionDao;
import com.changhong.sei.deploy.dto.ApplyType;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import com.changhong.sei.deploy.dto.ReleaseRecordRequisitionDto;
import com.changhong.sei.deploy.entity.ReleaseRecord;
import com.changhong.sei.deploy.entity.ReleaseRecordRequisition;
import com.changhong.sei.deploy.entity.ReleaseVersion;
import com.changhong.sei.deploy.entity.RequisitionOrder;
import com.changhong.sei.integrated.service.GitlabService;
import org.gitlab4j.api.models.Release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * 发布记录(ReleaseRecord)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("releaseRecordService")
public class ReleaseRecordService extends BaseEntityService<ReleaseRecord> {
    @Autowired
    private ReleaseRecordDao dao;
    @Autowired
    private ReleaseRecordRequisitionDao requisitionDao;
    @Autowired
    private ReleaseVersionService releaseVersionService;
    @Autowired
    private RequisitionOrderService requisitionOrderService;
    @Autowired
    private GitlabService gitlabService;

    @Override
    protected BaseEntityDao<ReleaseRecord> getDao() {
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
        ReleaseRecord app = this.findOne(id);
        if (Objects.isNull(app)) {
            return OperateResult.operationFailure("[" + id + "]发布记录不存在,删除失败!");
        }
        if (!app.getFrozen()) {
            return OperateResult.operationFailure("[" + id + "]发布记录已审核确认,不允许删除!");
        }
        return super.preDelete(id);
    }

    /**
     * 分页查询应用申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    public PageResult<ReleaseRecordRequisition> findRequisitionByPage(Search search) {
        return requisitionDao.findByPage(search);
    }

    /**
     * 创建应用申请单
     *
     * @param releaseRecord 应用
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<ReleaseRecordRequisitionDto> createRequisition(ReleaseRecord releaseRecord) {
        // 申请是设置为冻结状态,带申请审核确认后再值为可用状态
        releaseRecord.setFrozen(Boolean.TRUE);
        // 保存应用
        OperateResultWithData<ReleaseRecord> resultWithData = this.save(releaseRecord);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionOrder = new RequisitionOrder();
            // 申请类型:发布申请
            requisitionOrder.setApplicationType(ApplyType.PUBLISH);
            // 发布记录id
            requisitionOrder.setRelationId(releaseRecord.getId());
            // 申请摘要
            requisitionOrder.setSummary(releaseRecord.getAppName().concat("-").concat(releaseRecord.getAppName())
                    .concat("[").concat(releaseRecord.getName()).concat("]"));

            ResultData<RequisitionOrder> result = requisitionOrderService.createRequisition(requisitionOrder);
            if (result.successful()) {
                RequisitionOrder requisition = result.getData();
                ReleaseRecordRequisitionDto dto = new ReleaseRecordRequisitionDto();
                dto.setId(requisition.getId());
                dto.setApplicantAccount(requisition.getApplicantAccount());
                dto.setApplicantUserName(requisition.getApplicantUserName());
                dto.setApplicationTime(requisition.getApplicationTime());
                dto.setApplyType(requisition.getApplicationType());
                dto.setApprovalStatus(requisition.getApprovalStatus());

                dto.setRelationId(releaseRecord.getId());
                dto.setEnvCode(releaseRecord.getEnvCode());
                dto.setEnvName(releaseRecord.getEnvName());
                dto.setAppId(releaseRecord.getAppId());
                dto.setAppName(releaseRecord.getAppName());
                dto.setGitId(releaseRecord.getGitId());
                dto.setModuleName(releaseRecord.getModuleName());
                dto.setTagName(releaseRecord.getTagName());
                dto.setName(releaseRecord.getName());
                dto.setRemark(releaseRecord.getRemark());
                dto.setExpCompleteTime(releaseRecord.getExpCompleteTime());
                return ResultData.success(dto);
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
     * @param releaseRecord 应用
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<ReleaseRecordRequisitionDto> modifyRequisition(ReleaseRecord releaseRecord) {
        ReleaseRecord entity = this.findOne(releaseRecord.getId());
        if (Objects.isNull(entity)) {
            return ResultData.fail("应用不存在!");
        }
        // 检查应用审核状态
        if (!entity.getFrozen()) {
            return ResultData.fail("应用已审核,不允许编辑!");
        }


        entity.setEnvCode(releaseRecord.getEnvCode());
        entity.setEnvName(releaseRecord.getEnvName());
        entity.setAppId(releaseRecord.getAppId());
        entity.setAppName(releaseRecord.getAppName());
        entity.setGitId(releaseRecord.getGitId());
        entity.setModuleName(releaseRecord.getModuleName());
        entity.setTagName(releaseRecord.getTagName());
        entity.setName(releaseRecord.getName());
        entity.setRemark(releaseRecord.getRemark());
        entity.setExpCompleteTime(releaseRecord.getExpCompleteTime());

        // 保存应用
        OperateResultWithData<ReleaseRecord> resultWithData = this.save(entity);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionOrder = requisitionOrderService.getByRelationId(entity.getId());
            if (Objects.isNull(requisitionOrder)) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail("申请单不存在!");
            }
            // 检查申请单是否已审核
            if (ApprovalStatus.INITIAL != requisitionOrder.getApprovalStatus()) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail("申请单不存在!");
            }

            // 申请类型:发布申请
            requisitionOrder.setApplicationType(ApplyType.PUBLISH);
            // 发布记录id
            requisitionOrder.setRelationId(entity.getId());
            // 申请摘要
            requisitionOrder.setSummary(releaseRecord.getAppName().concat("-").concat(releaseRecord.getAppName())
                    .concat("[").concat(releaseRecord.getName()).concat("]"));

            ResultData<RequisitionOrder> result = requisitionOrderService.modifyRequisition(requisitionOrder);
            if (result.successful()) {
                RequisitionOrder requisition = result.getData();
                ReleaseRecordRequisitionDto dto = new ReleaseRecordRequisitionDto();
                dto.setId(requisition.getId());
                dto.setApplicantAccount(requisition.getApplicantAccount());
                dto.setApplicantUserName(requisition.getApplicantUserName());
                dto.setApplicationTime(requisition.getApplicationTime());
                dto.setApplyType(requisition.getApplicationType());
                dto.setApprovalStatus(requisition.getApprovalStatus());
                dto.setRelationId(releaseRecord.getId());

                dto.setEnvCode(releaseRecord.getEnvCode());
                dto.setEnvName(releaseRecord.getEnvName());
                dto.setAppId(releaseRecord.getAppId());
                dto.setAppName(releaseRecord.getAppName());
                dto.setGitId(releaseRecord.getGitId());
                dto.setModuleName(releaseRecord.getModuleName());
                dto.setTagName(releaseRecord.getTagName());
                dto.setName(releaseRecord.getName());
                dto.setRemark(releaseRecord.getRemark());
                dto.setExpCompleteTime(releaseRecord.getExpCompleteTime());
                return ResultData.success(dto);
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
        ReleaseRecord releaseRecord = this.findOne(id);
        if (Objects.nonNull(releaseRecord)) {
            if (releaseRecord.getFrozen()) {
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

    /**
     * 流程审核完成,更新冻结状态为:启用
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> updateFrozen(String id) {
        ReleaseRecord releaseRecord = this.findOne(id);
        if (Objects.nonNull(releaseRecord)) {
            releaseRecord.setFrozen(Boolean.FALSE);
        }
        // todo 异步调用Jenkins构建


        // todo Jenkins构建成功,调用gitlab创建版本
        ResultData<Release> resultData = gitlabService.createProjectRelease(releaseRecord.getGitId(), releaseRecord.getName(), releaseRecord.getTagName(), releaseRecord.getName());
        if (resultData.successful()) {
            Release gitlabRelease = resultData.getData();
            ReleaseVersion version = new ReleaseVersion();
            version.setAppId(releaseRecord.getAppId());
            version.setAppName(releaseRecord.getAppName());
            version.setGitId(releaseRecord.getGitId());
            version.setModuleName(releaseRecord.getModuleName());
            version.setName(releaseRecord.getName());
            version.setCommitId(gitlabRelease.getCommit().getId());
            // 约定镜像命名规范
            version.setImageName(releaseRecord.getModuleName() + "/" + gitlabRelease.getTagName());
            version.setVersion(gitlabRelease.getTagName());
            version.setRemark(gitlabRelease.getDescription());
            version.setCreateTime(LocalDateTime.now());
            releaseVersionService.save(version);
        }

        OperateResultWithData<ReleaseRecord> result = this.save(releaseRecord);
        if (result.successful()) {
            return ResultData.success();
        } else {
            return ResultData.fail(result.getMessage());
        }
    }
}