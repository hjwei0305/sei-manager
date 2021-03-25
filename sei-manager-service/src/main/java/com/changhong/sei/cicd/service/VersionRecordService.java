package com.changhong.sei.cicd.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.cicd.dao.VersionRecordDao;
import com.changhong.sei.cicd.dao.VersionRecordRequisitionDao;
import com.changhong.sei.cicd.dto.*;
import com.changhong.sei.cicd.entity.*;
import com.changhong.sei.ge.entity.AppModule;
import com.changhong.sei.ge.entity.MessageContent;
import com.changhong.sei.ge.service.AppModuleService;
import com.changhong.sei.ge.service.MessageContentService;
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
@Service
public class VersionRecordService extends BaseEntityService<VersionRecord> {
    @Autowired
    private VersionRecordDao dao;
    @Autowired
    private VersionRecordRequisitionDao versionRequisitionDao;

    @Autowired
    private AppModuleService moduleService;
    @Autowired
    private RequisitionOrderService requisitionOrderService;
    @Autowired
    private BuildJobService recordService;

    @Autowired
    private GitlabService gitlabService;
    @Autowired
    private MessageContentService messageContentService;

    @Override
    protected BaseEntityDao<VersionRecord> getDao() {
        return dao;
    }

    /**
     * 基于主键查询单一数据对象
     */
    @Override
    public VersionRecord findOne(String s) {
        VersionRecord record = dao.findOne(s);
        if (Objects.nonNull(record)) {
            String contentId = record.getMessageId();
            if (StringUtils.isNotBlank(contentId)) {
                String content = messageContentService.getContent(contentId);
                record.setRemark(content);
            }
        }
        return record;
    }

    /**
     * 分页查询应用模块申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    public PageResult<VersionRecordRequisition> findRequisitionByPage(Search search) {
        if (Objects.isNull(search)) {
            search = Search.createSearch();
        }
        search.addFilter(new SearchFilter(VersionRecordRequisition.APPLICANT_ACCOUNT, ContextUtil.getUserAccount()));

        return versionRequisitionDao.findByPage(search);
    }

    /**
     * 创建应用模块申请单
     *
     * @param versionRecord 模块
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<ReleaseVersionRequisitionDto> createRequisition(VersionRecord versionRecord) {
        // 重置版本
        versionRecord.setVersion(versionRecord.getRefTag() + "-Release");
        // 通过模块和版本检查是否重复申请
        VersionRecord existed = getByVersion(versionRecord.getAppId(), versionRecord.getModuleCode(), versionRecord.getVersion());
        if (Objects.nonNull(existed)) {
            return ResultData.fail("应用模块[" + versionRecord.getModuleCode() + "]对应版本[" + versionRecord.getVersion() + "]存在申请记录,请不要重复申请.");
        }

        // 申请是设置为冻结状态,带申请审核确认后再值为可用状态
        versionRecord.setFrozen(Boolean.TRUE);
        // 保存发版记录
        String content = versionRecord.getRemark();
        if (StringUtils.isNotBlank(content)) {
            MessageContent messageContent = new MessageContent(content);
            messageContentService.save(messageContent);
            versionRecord.setMessageId(messageContent.getId());
        }
        // 保存应用模块
        OperateResultWithData<VersionRecord> resultWithData = this.save(versionRecord);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionOrder = new RequisitionOrder();
            // 申请类型:应用版本申请
            requisitionOrder.setApplyType(ApplyType.PUBLISH);
            // 应用版本id
            requisitionOrder.setRelationId(versionRecord.getId());
            // 申请摘要
            requisitionOrder.setSummary(versionRecord.getName().concat("[").concat(versionRecord.getVersion()).concat("]"));

            ResultData<RequisitionOrder> result = requisitionOrderService.createRequisition(requisitionOrder);
            if (result.successful()) {
                RequisitionOrder requisition = result.getData();
                ReleaseVersionRequisitionDto dto = new ReleaseVersionRequisitionDto();
                dto.setId(requisition.getId());
                dto.setApplicantAccount(requisition.getApplicantAccount());
                dto.setApplicantUserName(requisition.getApplicantUserName());
                dto.setApplicationTime(requisition.getApplicationTime());
                dto.setApplyType(requisition.getApplyType());
                dto.setApprovalStatus(requisition.getApprovalStatus());

                dto.setRelationId(versionRecord.getId());
                dto.setAppId(versionRecord.getAppId());
                dto.setAppName(versionRecord.getAppName());
                dto.setGitId(versionRecord.getGitId());
                dto.setModuleId(versionRecord.getModuleId());
                dto.setModuleCode(versionRecord.getModuleCode());
                dto.setModuleName(versionRecord.getModuleName());
                dto.setRefTagId(versionRecord.getRefTagId());
                dto.setRefTag(versionRecord.getRefTag());
                dto.setName(versionRecord.getName());
                dto.setVersion(versionRecord.getVersion());
                dto.setRemark(content);
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
    public ResultData<ReleaseVersionRequisitionDto> modifyRequisition(VersionRecord releaseVersion) {
        // 重置版本
        releaseVersion.setVersion(releaseVersion.getRefTag() + "-Release");
        // 通过模块和版本检查是否重复申请
        VersionRecord existed = getByVersion(releaseVersion.getAppId(), releaseVersion.getModuleCode(), releaseVersion.getVersion());
        if (Objects.nonNull(existed)) {
            return ResultData.fail("应用模块[" + releaseVersion.getModuleCode() + "]对应版本[" + releaseVersion.getVersion() + "]存在申请记录,请不要重复申请.");
        }
        VersionRecord version = dao.findOne(releaseVersion.getId());
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
        version.setModuleId(releaseVersion.getModuleId());
        version.setModuleCode(releaseVersion.getModuleCode());
        version.setModuleName(releaseVersion.getModuleName());

        version.setRefTagId(releaseVersion.getRefTagId());
        version.setRefTag(releaseVersion.getRefTag());
        version.setName(releaseVersion.getName());
        version.setVersion(releaseVersion.getVersion());
        // 更新发版记录
        String content = releaseVersion.getRemark();
        if (StringUtils.isNotBlank(content)) {
            MessageContent messageContent;
            String messageId = version.getMessageId();
            if (StringUtils.isNotBlank(messageId)) {
                messageContent = messageContentService.findOne(messageId);
                if (Objects.isNull(messageContent)) {
                    messageContent = new MessageContent(content);
                } else {
                    messageContent.setContent(content);
                }
            } else {
                messageContent = new MessageContent(content);
            }
            messageContentService.save(messageContent);
            version.setMessageId(messageContent.getId());
        }

        // 保存应用模块
        OperateResultWithData<VersionRecord> resultWithData = this.save(version);
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
            requisitionOrder.setApplyType(ApplyType.PUBLISH);
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
                dto.setApplyType(requisition.getApplyType());
                dto.setApprovalStatus(requisition.getApprovalStatus());

                dto.setRelationId(releaseVersion.getId());
                dto.setAppId(releaseVersion.getAppId());
                dto.setAppName(releaseVersion.getAppName());
                dto.setGitId(releaseVersion.getGitId());
                dto.setModuleId(releaseVersion.getModuleId());
                dto.setModuleCode(releaseVersion.getModuleCode());
                dto.setModuleName(releaseVersion.getModuleName());
                dto.setRefTagId(releaseVersion.getRefTagId());
                dto.setRefTag(releaseVersion.getRefTag());
                dto.setName(releaseVersion.getName());
                dto.setVersion(releaseVersion.getVersion());
                dto.setRemark(content);
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
        VersionRecord version = dao.findOne(id);
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

    /**
     * 更新审核状态
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> updateFrozen(String id) {
        VersionRecord version = this.findOne(id);
        if (Objects.isNull(version)) {
            return ResultData.fail("版本申请单[" + id + "]不存在");
        }

        AppModule module = moduleService.getAppModuleByGitId(version.getGitId());
        if (Objects.isNull(module)) {
            return ResultData.fail("应用模块[" + version.getModuleCode() + "]不存在");
        }
        ResultData<Release> resultData = gitlabService.createProjectRelease(version.getGitId(), version.getName(), version.getVersion(), version.getRefTag(), version.getRemark());
        if (resultData.failed()) {
            return ResultData.fail(module.getCode() + "-版本[" + version.getVersion() + "]发布失败: " + resultData.getMessage());
        }
        Release gitlabRelease = resultData.getData();
        version.setModuleId(module.getId());
        version.setCommitId(gitlabRelease.getCommit().getId());
        // 创建时间
        version.setCreateTime(LocalDateTime.now());
        // 创建人
        version.setCreateUser(ContextUtil.getUserAccount());
        // 约定镜像命名规范
        version.setImageName(version.getModuleCode() + ":" + version.getVersion());

        // 申请通过,修改为可用
        version.setFrozen(Boolean.FALSE);
        version.setBuildStatus(BuildStatus.BUILDING);
        this.save(version);

        BuildJob record = recordService.getByGitIdAndTag(version.getGitId(), version.getVersion());
        if (Objects.isNull(record)) {
            // 生成一条构建记录
            record = new BuildJob();
            if (StringUtils.isBlank(module.getNameSpace())) {
                // 前端应用
                record.setType(TemplateType.PUBLISH_WEB.name());
            } else {
                // java应用
                record.setType(TemplateType.PUBLISH_JAVA.name());
            }
            record.setAppId(version.getAppId());
            record.setAppName(version.getAppName());
            record.setGitId(version.getGitId());
            record.setModuleId(version.getModuleId());
            record.setModuleCode(version.getModuleCode());
            record.setModuleName(version.getModuleName());
            record.setRefTagId(version.getRefTagId());
            record.setRefTag(version.getVersion());
            record.setName(version.getName());
            record.setMessageId(version.getMessageId());
            record.setFrozen(Boolean.FALSE);
            record.setExpCompleteTime(LocalDateTime.now());
            recordService.save(record);
        }

        // 发起Jenkins构建ø
        SessionUser user = ContextUtil.getSessionUser();
        ResultData<BuildJob> buildResult = recordService.build(record.getId(), user.getAccount());
        if (buildResult.failed()) {
            return ResultData.fail(resultData.getMessage());
        }
        // 保存构建号及状态
        BuildJob releaseRecord = buildResult.getData();
        OperateResultWithData<BuildJob> result = recordService.save(releaseRecord);
        if (result.successful()) {
            return ResultData.success();
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    public VersionRecord getByVersion(String appId, String moduleCode, String version) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(VersionRecord.FIELD_APP_ID, appId));
        search.addFilter(new SearchFilter(VersionRecord.FIELD_MODULE_CODE, moduleCode));
        search.addFilter(new SearchFilter(VersionRecord.FIELD_VERSION, version));
        return dao.findFirstByFilters(search);
    }


    /**
     * 发布版本
     * Jenkins构建成功,调用gitlab创建版本
     *
     * @param record 构建记录
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> releaseVersion(BuildJob record) {
        if (Objects.isNull(record)) {
            return ResultData.fail("发布记录不能为空.");
        }

        VersionRecord version = this.getByVersion(record.getAppId(), record.getModuleCode(), record.getRefTag());
        if (Objects.isNull(version)) {
            return ResultData.fail(record.getModuleCode() + "版本[" + record.getRefTag() + "]不存在.");
        }

        if (BuildStatus.SUCCESS == record.getBuildStatus()) {
            // 构建成功,更新版本状态为可用
            version.setAvailable(Boolean.TRUE);
            version.setBuildStatus(record.getBuildStatus());
            this.save(version);
            LogUtil.bizLog(record.getJobName() + "发版成功[" + record.getRefTag() + "].");
            // 更新应用模块当前最新版本号
            moduleService.updateVersion(record.getModuleId(), record.getRefTag());

            return ResultData.success();
        } else {
            version.setBuildStatus(record.getBuildStatus());
            this.save(version);
            // 发版失败,删除gitlab的版本
            gitlabService.deleteProjectRelease(record.getGitId(), record.getRefTag());

            return ResultData.fail(record.getJobName() + "发版失败,当前构建状态:" + record.getBuildStatus().name());
        }
    }
}