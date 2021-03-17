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
import com.changhong.sei.cicd.dao.ReleaseVersionDao;
import com.changhong.sei.cicd.dao.ReleaseVersionRequisitionDao;
import com.changhong.sei.cicd.dto.*;
import com.changhong.sei.cicd.entity.*;
import com.changhong.sei.ge.entity.AppModule;
import com.changhong.sei.ge.service.AppModuleService;
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
    private BuildJobService recordService;

    @Autowired
    private GitlabService gitlabService;

    @Override
    protected BaseEntityDao<ReleaseVersion> getDao() {
        return dao;
    }

    /**
     * 分页查询应用模块申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    public PageResult<ReleaseVersionRequisition> findRequisitionByPage(Search search) {
        if (Objects.isNull(search)) {
            search = Search.createSearch();
        }
        search.addFilter(new SearchFilter(ReleaseVersionRequisition.APPLICANT_ACCOUNT, ContextUtil.getUserAccount()));

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
        // 重置版本
        releaseVersion.setVersion(releaseVersion.getRefTag() + "-Release");
        // 通过模块和版本检查是否重复申请
        ReleaseVersion existed = getByVersion(releaseVersion.getAppId(), releaseVersion.getModuleCode(), releaseVersion.getVersion());
        if (Objects.nonNull(existed)) {
            return ResultData.fail("应用模块[" + releaseVersion.getModuleCode() + "]对应版本[" + releaseVersion.getVersion() + "]存在申请记录,请不要重复申请.");
        }

        // 申请是设置为冻结状态,带申请审核确认后再值为可用状态
        releaseVersion.setFrozen(Boolean.TRUE);
        // 保存应用模块
        OperateResultWithData<ReleaseVersion> resultWithData = this.save(releaseVersion);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionOrder = new RequisitionOrder();
            // 申请类型:应用版本申请
            requisitionOrder.setApplyType(ApplyType.PUBLISH);
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
                dto.setApplyType(requisition.getApplyType());
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
        // 重置版本
        releaseVersion.setVersion(releaseVersion.getRefTag() + "-Release");
        // 通过模块和版本检查是否重复申请
        ReleaseVersion existed = getByVersion(releaseVersion.getAppId(), releaseVersion.getModuleCode(), releaseVersion.getVersion());
        if (Objects.nonNull(existed)) {
            return ResultData.fail("应用模块[" + releaseVersion.getModuleCode() + "]对应版本[" + releaseVersion.getVersion() + "]存在申请记录,请不要重复申请.");
        }
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
                dto.setModuleCode(releaseVersion.getModuleCode());
                dto.setModuleName(releaseVersion.getModuleName());
                dto.setRefTag(releaseVersion.getRefTag());
                dto.setName(releaseVersion.getName());
                dto.setVersion(releaseVersion.getVersion());
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

    /**
     * 更新审核状态
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> updateFrozen(String id) {
        ReleaseVersion version = dao.findOne(id);
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
            record.setModuleCode(version.getModuleCode());
            record.setModuleName(version.getModuleName());
            record.setTagName(version.getVersion());
            record.setName(version.getName());
            record.setRemark(version.getRemark());
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

    public ReleaseVersion getByVersion(String appId, String moduleCode, String version) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(ReleaseVersion.FIELD_APP_ID, appId));
        search.addFilter(new SearchFilter(ReleaseVersion.FIELD_MODULE_CODE, moduleCode));
        search.addFilter(new SearchFilter(ReleaseVersion.FIELD_VERSION, version));
        return dao.findFirstByFilters(search);
    }


    /**
     * 发布版本
     * Jenkins构建成功,调用gitlab创建版本
     *
     * @param record 构建记录
     */
    @Transactional
    public ResultData<Void> releaseVersion(BuildJob record) {
        if (Objects.isNull(record)) {
            return ResultData.fail("发布记录不能为空.");
        }

        ReleaseVersion version = this.getByVersion(record.getAppId(), record.getModuleCode(), record.getTagName());
        if (Objects.isNull(version)) {
            return ResultData.fail(record.getModuleCode() + "版本[" + record.getTagName() + "]不存在.");
        }

        if (BuildStatus.SUCCESS == record.getBuildStatus()) {
            // 构建成功,更新版本状态为可用
            version.setAvailable(Boolean.TRUE);
            version.setBuildStatus(record.getBuildStatus());
            this.save(version);
            LogUtil.bizLog(record.getJobName() + "发版成功[" + record.getTagName() + "].");

            moduleService.updateVersion(record.getGitId(), record.getTagName());

            return ResultData.success();
        } else {
            version.setBuildStatus(record.getBuildStatus());
            this.save(version);

            gitlabService.deleteProjectRelease(record.getGitId(), record.getTagName());

            return ResultData.fail(record.getJobName() + "发版失败,当前构建状态:" + record.getBuildStatus().name());
        }
    }
}