package com.changhong.sei.deploy.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.common.Constants;
import com.changhong.sei.deploy.dao.ReleaseBuildDetailDao;
import com.changhong.sei.deploy.dao.ReleaseRecordDao;
import com.changhong.sei.deploy.dao.ReleaseRecordRequisitionDao;
import com.changhong.sei.deploy.dto.*;
import com.changhong.sei.deploy.entity.*;
import com.changhong.sei.integrated.service.JenkinsService;
import com.changhong.sei.util.EnumUtils;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.ConsoleLog;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


/**
 * 发布记录(ReleaseRecord)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("releaseRecordService")
public class ReleaseRecordService extends BaseEntityService<ReleaseRecord> {
    private static final Logger LOG = LoggerFactory.getLogger(ReleaseRecordService.class);
    @Autowired
    private ReleaseRecordDao dao;
    @Autowired
    private ReleaseRecordRequisitionDao requisitionDao;
    @Autowired
    private ReleaseBuildDetailDao buildDetailDao;
    @Autowired
    private AppModuleService moduleService;
    @Autowired
    private RequisitionOrderService requisitionOrderService;
    @Autowired
    private DeployConfigService deployConfigService;
    @Autowired
    private DeployTemplateService templateService;
    @Autowired
    private DeployTemplateStageService deployTemplateStageService;
    @Autowired
    private JenkinsService jenkinsService;
    @Autowired
    private ReleaseVersionService releaseVersionService;
    @Autowired
    private ModelMapper modelMapper;

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
            requisitionOrder.setApplicationType(ApplyType.DEPLOY);
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
        entity.setModuleCode(releaseRecord.getModuleCode());
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
            requisitionOrder.setApplicationType(ApplyType.DEPLOY);
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
     * 流程审核完成,执行Jenkins构建任务,更新冻结状态为:启用
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> updateFrozen(String id) {
        SessionUser user = ContextUtil.getSessionUser();
        ResultData<ReleaseRecord> resultData = build(id, user.getAccount());
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }

        ReleaseRecord releaseRecord = resultData.getData();
        releaseRecord.setFrozen(Boolean.FALSE);
        OperateResultWithData<ReleaseRecord> result = this.save(releaseRecord);
        if (result.successful()) {
            return ResultData.success();
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 构建Jenkins任务
     *
     * @param id 发布记录id
     * @return 返回构建操作
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> buildJob(String id) {
        SessionUser user = ContextUtil.getSessionUser();
        ResultData<ReleaseRecord> resultData = build(id, user.getAccount());
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }

        ReleaseRecord releaseRecord = resultData.getData();
        OperateResultWithData<ReleaseRecord> result = this.save(releaseRecord);
        if (result.successful()) {
            return ResultData.success();
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 获取构建阶段
     *
     * @param id 发布记录id
     * @return 返回构建阶段
     */
    public ResultData<ReleaseRecordDetailDto> getBuildDetail(String id) {
        ReleaseRecord releaseRecord = this.findOne(id);
        if (Objects.isNull(releaseRecord)) {
            return ResultData.fail("发布记录不存在");
        }

        ReleaseRecordDetailDto detailDto = modelMapper.map(releaseRecord, ReleaseRecordDetailDto.class);

        ResultData<DeployConfig> resultData = deployConfigService.getDeployConfig(releaseRecord.getEnvCode(), releaseRecord.getModuleCode());
        if (resultData.successful()) {
            DeployConfig config = resultData.getData();
            ResultData<List<DeployTemplateStageResponse>> result = deployTemplateStageService.getStageByTemplateId(config.getTempId());
            detailDto.setStages(result.getData());

            if (BuildStatus.BUILDING != releaseRecord.getBuildStatus()) {
                Search search = Search.createSearch();
                search.addFilter(new SearchFilter(ReleaseBuildDetail.FIELD_RECORD_ID, id));
                search.addFilter(new SearchFilter(ReleaseBuildDetail.FIELD_BUILD_NUMBER, releaseRecord.getBuildNumber()));
                ReleaseBuildDetail detail = buildDetailDao.findFirstByFilters(search);
                if (Objects.nonNull(detail)) {
                    detailDto.setBuildLog(detail.getBuildLog());
                }
            }
        } else {
            return ResultData.fail(resultData.getMessage());
        }

        return ResultData.success(detailDto);
    }

    /**
     * 构建
     *
     * @param recordId 发布记录Id
     * @return 返回发布记录
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<ReleaseRecord> build(String recordId, String account) {
        ReleaseRecord releaseRecord = this.findOne(recordId);
        if (Objects.nonNull(releaseRecord)) {
            final String jobName;
            final boolean needRelease;
            if (StringUtils.equals(TemplateType.DEPLOY.name(), releaseRecord.getType())) {
                // 检查部署配置是否存在
                ResultData<DeployConfig> resultData = deployConfigService.getDeployConfig(releaseRecord.getEnvCode(), releaseRecord.getModuleCode());
                if (resultData.failed()) {
                    return ResultData.fail(resultData.getMessage());
                }
                jobName = releaseRecord.getJobName();
                needRelease = false;
            } else {
                // 检查
                ResultData<DeployTemplate> resultData = templateService.getPublishTemplate(releaseRecord.getType());
                if (resultData.failed()) {
                    return ResultData.fail(resultData.getMessage());
                }
                // @see templateService#syncJenkinsJob
                DeployTemplate deployTemplate = resultData.getData();
                jobName = deployTemplate.getName();
                needRelease = true;
            }

            Map<String, String> params = new HashMap<>();
            // 参数:项目名称(模块代码)
            params.put(Constants.DEPLOY_PARAM_PROJECT_NAME, releaseRecord.getModuleCode());
            // 参数:应用git仓库地址
            AppModule module = moduleService.getAppModule(releaseRecord.getModuleCode());
            params.put(Constants.DEPLOY_PARAM_GIT_PATH, Objects.isNull(module) ? "null" : module.getGitHttpUrl());
            // 参数:代码分支或者TAG
            params.put(Constants.DEPLOY_PARAM_BRANCH, releaseRecord.getTagName());

            // 调用Jenkins构建
            ResultData<Integer> buildResult = jenkinsService.buildJob(jobName, params);
            if (buildResult.successful()) {
                final int buildNumber = buildResult.getData();
                releaseRecord.setBuildNumber(buildNumber);
                // 更新构建状态为构建中
                releaseRecord.setBuildStatus(BuildStatus.BUILDING);

                // 异步上传
                CompletableFuture.runAsync(() -> ContextUtil.getBean(ReleaseRecordService.class).runBuild(recordId, jobName, buildNumber, account, needRelease));
            } else {
                releaseRecord.setBuildStatus(BuildStatus.FAILURE);
            }
            // 构建时间
            releaseRecord.setBuildTime(LocalDateTime.now());
            // 构建人账号
            releaseRecord.setBuildAccount(account);
            return ResultData.success(releaseRecord);
        } else {
            return ResultData.fail("发布记录不存在");
        }
    }

    /**
     * 调用Jenkins构建任务
     */
    @Transactional(rollbackFor = Exception.class)
    public void runBuild(String id, String jobName, int buildNumber, String account, boolean needRelease) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        ReleaseBuildDetail detail = new ReleaseBuildDetail();
        detail.setRecordId(id);
        detail.setJobName(jobName);
        detail.setBuildNumber(buildNumber);
        detail.setBuildAccount(account);

        StringBuilder log = new StringBuilder(32);
        try (JenkinsServer jenkinsServer = jenkinsService.getJenkinsServer()) {
            JobWithDetails details = jenkinsServer.getJob(jobName);
            if (Objects.isNull(details)) {
                LOG.debug("{}任务不存在,开始循环检查任务.", jobName);
                int times = 0;
                // 睡眠 10秒
                int sleepTime = 10 * 1000;
                boolean isContinue = true;
                while (isContinue && times <= 5) {
                    times++;
                    // 最长等待20分钟
                    sleepTime = sleepTime * times;
                    Thread.sleep(sleepTime);
                    details = jenkinsServer.getJob(jobName);
                    if (Objects.nonNull(details)) {
                        isContinue = false;
                    }
                }
            }

            LOG.debug("{}任务是否在队列中:{}", jobName, details.isInQueue());
            Build build = details.getBuildByNumber(buildNumber);
            int times = 0;
            // 睡眠 10秒
            int sleepTime = 10 * 1000;
            while ((Objects.isNull(build) || details.isInQueue()) && times <= 5) {
                times++;
                // 最长等待20分钟
                sleepTime = sleepTime * times;
                //noinspection BusyWait
                Thread.sleep(sleepTime);
                details = jenkinsServer.getJob(jobName);
                build = details.getBuildByNumber(buildNumber);
            }
            LOG.debug("{}任务开始构建...", jobName);
            BuildWithDetails withDetails = build.details();

            // 当前日志
            ConsoleLog currentLog = jenkinsService.getConsoleOutputText(withDetails, 0);
            // 输出当前获取日志信息
            log.append(currentLog.getConsoleLog());
            // 检测是否还有更多日志,如果是则继续循环获取
            while (currentLog.getHasMoreData()) {
                // 睡眠30s
                //noinspection BusyWait
                Thread.sleep(5000);
                // 获取最新日志信息
                ConsoleLog newLog = jenkinsService.getConsoleOutputText(withDetails, currentLog.getCurrentBufferSize());
                // 输出最新日志
                log.append(newLog.getConsoleLog());
                currentLog = newLog;
            }

            detail.setBuildLog(log.toString());

            withDetails = details.getBuildByNumber(buildNumber).details();
            detail.setStartTime(withDetails.getTimestamp());
            detail.setDuration(withDetails.getDuration());
            detail.setBuildStatus(EnumUtils.getEnum(BuildStatus.class, withDetails.getResult().name()));
        } catch (Exception e) {
            detail.setBuildLog("获取Jenkins任务[" + jobName + "]的构建日志异常:" + ExceptionUtils.getRootCauseMessage(e));
            detail.setStartTime(System.currentTimeMillis());
            detail.setBuildStatus(BuildStatus.FAILURE);

            LOG.error("获取Jenkins任务[" + jobName + "]的构建日志异常:" + ExceptionUtils.getRootCauseMessage(e), e);
        }

        buildDetailDao.save(detail);

        ReleaseRecord record = dao.findOne(id);
        if (Objects.nonNull(record)) {
            BuildStatus status = detail.getBuildStatus();
            // 更新发布记录状态
            record.setBuildStatus(status);
            dao.save(record);
            if (BuildStatus.SUCCESS == status && needRelease) {
                releaseVersionService.releaseVersion(record);
            }
        }
    }

    public ReleaseRecord getByGitIdAndTag(String gitId, String tag) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(ReleaseRecord.FIELD_GIT_ID, gitId));
        search.addFilter(new SearchFilter(ReleaseRecord.FIELD_TAG_NAME, tag));
        return dao.findFirstByFilters(search);
    }

    /**
     * Gitlab Push Hook
     *
     * @param request gitlab push hook
     * @return 返回结果
     */
    @Transactional
    public ResultData<Void> webhook(GitlabPushHookRequest request) {
        if (!StringUtils.equalsIgnoreCase("refs/heads/dev", request.getRef())) {
            // 只对dev分支做自动发布,其他分支跳过
            return ResultData.success();
        }
        String gitId = request.getGitId();
        AppModule module = moduleService.findByProperty(AppModule.FIELD_GIT_ID, gitId);
        if (Objects.isNull(module)) {
            return ResultData.fail("未找到Git Id[" + gitId + "]对应的应用模块");
        }
        String tag = "dev";
        try {
            ReleaseRecord record = getByGitIdAndTag(gitId, tag);
            if (Objects.isNull(record)) {
                record = new ReleaseRecord();
                record.setEnvCode("dev");
                record.setEnvName("开发环境");
                record.setAppId(module.getAppId());
                record.setAppName(module.getAppName());
                record.setGitId(gitId);
                record.setModuleCode(module.getCode());
                record.setModuleName(module.getName());
                record.setTagName("dev");
                record.setName("开发构建-" + module.getName());
                record.setRemark("开发构建-" + module.getName());
                record.setFrozen(Boolean.FALSE);
                record.setExpCompleteTime(LocalDateTime.now());
                this.save(record);
            }

            this.build(record.getId(), request.getUserUsername());
        } catch (Exception e) {
            LOG.error("{} 的Push Hook 异常{}", request, ExceptionUtils.getRootCauseMessage(e));
        }
        return ResultData.success();
    }
}
