package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.ReleaseVersionDao;
import com.changhong.sei.deploy.dao.ReleaseVersionRequisitionDao;
import com.changhong.sei.deploy.dto.*;
import com.changhong.sei.deploy.entity.*;
import com.changhong.sei.integrated.service.GitlabService;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * 版本发布记录(ReleaseVersion)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("releaseVersionService")
public class ReleaseVersionService extends BaseEntityService<ReleaseVersion> {
    @Autowired
    private ReleaseVersionDao dao;
    @Autowired
    private ReleaseVersionRequisitionDao versionRequisitionDao;

    @Autowired
    private AppModuleService moduleService;
    @Autowired
    private RequisitionOrderService requisitionOrderService;

    @Autowired
    private GitlabService gitlabService;

    @Override
    protected BaseEntityDao<ReleaseVersion> getDao() {
        return dao;
    }

    /**
     * 发布版本
     * Jenkins构建成功,调用gitlab创建版本
     *
     * @param releaseRecord 构建记录
     */
    @Transactional
    public ResultData<Void> releaseVersion(ReleaseRecord releaseRecord) {
        if (Objects.isNull(releaseRecord)) {
            return ResultData.fail("发布记录不能为空.");
        }
        if (BuildStatus.SUCCESS == releaseRecord.getBuildStatus()) {
            String gitId = releaseRecord.getGitId();
            String tag = releaseRecord.getTagName() + "-Release";
            String refTag = releaseRecord.getTagName();
            String versionName = releaseRecord.getName();
            if (StringUtils.isBlank(versionName)) {
                versionName = tag;
            }
            String remark = releaseRecord.getRemark();
            if (StringUtils.isBlank(remark)) {
                remark = versionName;
            }
//            LogUtil.bizLog("创建gitlab版本, gitId:{}, versionName:{}, tag:{}, refTag:{}, remark:{}, ", gitId, versionName, tag, refTag, remark);
            ResultData<Release> resultData = gitlabService.createProjectRelease(gitId, versionName, tag, refTag, remark);
            if (resultData.successful()) {
                Release gitlabRelease = resultData.getData();
                ReleaseVersion version = new ReleaseVersion();
                version.setAppId(releaseRecord.getAppId());
                version.setAppName(releaseRecord.getAppName());
                version.setGitId(releaseRecord.getGitId());
                version.setModuleName(releaseRecord.getModuleName());
                version.setName(versionName);
                version.setCommitId(gitlabRelease.getCommit().getId());
                // 约定镜像命名规范
                version.setImageName(releaseRecord.getModuleName() + ":" + tag);
                version.setVersion(tag);
                version.setRemark(remark);
                version.setCreateTime(LocalDateTime.now());
                this.save(version);
                LogUtil.bizLog(releaseRecord.getJobName() + "发版成功[" + releaseRecord.getTagName() + "].");

                moduleService.updateVersion(releaseRecord.getModuleCode(), releaseRecord.getTagName());

                return ResultData.success();
            } else {
                return ResultData.fail(releaseRecord.getJobName() + "发版失败: " + resultData.getMessage());
            }
        } else {
            return ResultData.fail(releaseRecord.getJobName() + "发版失败,当前构建状态:" + releaseRecord.getBuildStatus().name());
        }
    }


    /**
     * 分页查询应用模块申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    public PageResult<ReleaseVersionRequisition> findRequisitionByPage(Search search) {
        return versionRequisitionDao.findByPage(search);
    }

    /**
     * 创建应用模块申请单
     *
     * @param releaseVersion 模块
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<ReleaseVersionRequisitionDto> createRequisition(ReleaseVersion releaseVersion) {
        // 申请是设置为冻结状态,带申请审核确认后再值为可用状态
        releaseVersion.setFrozen(Boolean.TRUE);
        // 保存应用模块
        OperateResultWithData<ReleaseVersion> resultWithData = this.save(releaseVersion);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionOrder = new RequisitionOrder();
            // 申请类型:应用版本申请
            requisitionOrder.setApplicationType(ApplyType.PUBLISH);
            // 应用版本id
            requisitionOrder.setRelationId(releaseVersion.getId());
            // 申请摘要
            requisitionOrder.setSummary(releaseVersion.getName().concat("[").concat(releaseVersion.getVersion()).concat("]"));

            ResultData<RequisitionOrder> result = requisitionOrderService.createRequisition(requisitionOrder);
            if (result.successful()) {
                RequisitionOrder requisition = result.getData();
                ReleaseVersionRequisitionDto dto = new ReleaseVersionRequisitionDto();
                dto.setId(requisition.getId());
                dto.setApplicantAccount(requisition.getApplicantAccount());
                dto.setApplicantUserName(requisition.getApplicantUserName());
                dto.setApplicationTime(requisition.getApplicationTime());
                dto.setApplyType(requisition.getApplicationType());
                dto.setApprovalStatus(requisition.getApprovalStatus());

                dto.setRelationId(releaseVersion.getId());
                dto.setAppId(releaseVersion.getAppId());
                dto.setAppName(releaseVersion.getAppName());
                dto.setGitId(releaseVersion.getGitId());
                dto.setModuleCode(releaseVersion.getModuleCode());
                dto.setModuleName(releaseVersion.getModuleName());
                dto.setRefTag(releaseVersion.getRefTag());
                dto.setName(releaseVersion.getName());
                dto.setVersion(releaseVersion.getVersion());
                dto.setRemark(releaseVersion.getRemark());
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
     * 编辑修改应用模块申请单
     *
     * @param releaseVersion 应用模块
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<ReleaseVersionRequisitionDto> modifyRequisition(ReleaseVersion releaseVersion) {
        ReleaseVersion version = this.findOne(releaseVersion.getId());
        if (Objects.isNull(version)) {
            return ResultData.fail("应用模块不存在!");
        }
        // 检查应用审核状态
        if (!version.getFrozen()) {
            return ResultData.fail("应用模块已审核,不允许编辑!");
        }

        version.setAppId(releaseVersion.getAppId());
        version.setAppName(releaseVersion.getAppName());
        version.setGitId(releaseVersion.getGitId());
        version.setModuleCode(releaseVersion.getModuleCode());
        version.setModuleName(releaseVersion.getModuleName());

        version.setRefTag(releaseVersion.getRefTag());
        version.setName(releaseVersion.getName());
        version.setVersion(releaseVersion.getVersion());
        version.setRemark(releaseVersion.getRemark());

        // 保存应用模块
        OperateResultWithData<ReleaseVersion> resultWithData = this.save(version);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionOrder = requisitionOrderService.getByRelationId(version.getId());
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

            // 申请类型:应用模块申请
            requisitionOrder.setApplicationType(ApplyType.PUBLISH);
            // 应用模块id
            requisitionOrder.setRelationId(version.getId());
            // 申请摘要
            requisitionOrder.setSummary(version.getName().concat("[").concat(version.getVersion()).concat("]"));

            ResultData<RequisitionOrder> result = requisitionOrderService.modifyRequisition(requisitionOrder);
            if (result.successful()) {
                RequisitionOrder requisition = result.getData();
                ReleaseVersionRequisitionDto dto = new ReleaseVersionRequisitionDto();
                dto.setId(requisition.getId());
                dto.setApplicantAccount(requisition.getApplicantAccount());
                dto.setApplicantUserName(requisition.getApplicantUserName());
                dto.setApplicationTime(requisition.getApplicationTime());
                dto.setApplyType(requisition.getApplicationType());
                dto.setApprovalStatus(requisition.getApprovalStatus());

                dto.setRelationId(releaseVersion.getId());
                dto.setAppId(releaseVersion.getAppId());
                dto.setAppName(releaseVersion.getAppName());
                dto.setGitId(releaseVersion.getGitId());
                dto.setModuleCode(releaseVersion.getModuleCode());
                dto.setModuleName(releaseVersion.getModuleName());
                dto.setRefTag(releaseVersion.getRefTag());
                dto.setName(releaseVersion.getName());
                dto.setVersion(releaseVersion.getVersion());
                dto.setRemark(releaseVersion.getRemark());
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
     * 删除应用版本申请单
     *
     * @param id id
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> deleteRequisition(String id) {
        ReleaseVersion version = this.findOne(id);
        if (Objects.nonNull(version)) {
            if (version.getFrozen()) {
                // 删除应用版本申请
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
                return ResultData.fail("应用版本已审核,禁止删除!");
            }
        }
        return ResultData.fail("应用版本不存在!");
    }
}