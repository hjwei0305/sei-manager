package com.changhong.sei.cicd.service;

import com.changhong.sei.cicd.dao.BuildDetailDao;
import com.changhong.sei.cicd.dao.BuildJobDao;
import com.changhong.sei.cicd.dao.BuildJobRequisitionDao;
import com.changhong.sei.cicd.dto.*;
import com.changhong.sei.cicd.entity.*;
import com.changhong.sei.common.Constants;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.dto.serach.SearchOrder;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.ge.entity.AppModule;
import com.changhong.sei.ge.entity.MessageContent;
import com.changhong.sei.ge.service.AppModuleService;
import com.changhong.sei.ge.service.MessageContentService;
import com.changhong.sei.integrated.service.JenkinsService;
import com.changhong.sei.manager.commom.EmailManager;
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
import org.springframework.beans.factory.annotation.Value;
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
 * 发布记录(BuildJob)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service
public class BuildJobService extends BaseEntityService<BuildJob> {
    private static final Logger LOG = LoggerFactory.getLogger(BuildJobService.class);
    @Autowired
    private BuildJobDao dao;
    @Autowired
    private BuildJobRequisitionDao requisitionDao;
    @Autowired
    private BuildDetailDao buildDetailDao;
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
    private VersionRecordService releaseVersionService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MessageContentService messageContentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private EmailManager emailManager;

    @Value("${sei.application.name:SEI开发运维平台}")
    private String managerName;

    @Override
    protected BaseEntityDao<BuildJob> getDao() {
        return dao;
    }

    /**
     * 基于主键查询单一数据对象
     */
    @Override
    public BuildJob findOne(String s) {
        BuildJob record = dao.findOne(s);
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
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查状态控制删除
        BuildJob app = dao.findOne(id);
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
    public PageResult<BuildJobRequisition> findRequisitionByPage(Search search) {
        if (Objects.isNull(search)) {
            search = Search.createSearch();
        }
        search.addFilter(new SearchFilter(BuildJobRequisition.APPLICANT_ACCOUNT, ContextUtil.getUserAccount()));

        return requisitionDao.findByPage(search);
    }

    /**
     * 创建应用申请单
     *
     * @param buildJob 应用
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<BuildJobRequisitionDto> createRequisition(BuildJob buildJob) {
        // 通过模块和tag检查是否重复申请
        BuildJob existed = getByGitIdAndTag(buildJob.getGitId(), buildJob.getRefTag(), buildJob.getEnvCode(), buildJob.getType());
        if (Objects.nonNull(existed)) {
            return ResultData.fail("应用模块[" + buildJob.getModuleCode() + "]对应标签[" + buildJob.getRefTag() + "]存在申请记录,请不要重复申请.");
        }

        // 申请是设置为冻结状态,带申请审核确认后再值为可用状态
        buildJob.setFrozen(Boolean.TRUE);
        // 保存发版记录
        String content = buildJob.getRemark();
        if (StringUtils.isNotBlank(content)) {
            MessageContent messageContent = new MessageContent(content);
            messageContentService.save(messageContent);
            buildJob.setMessageId(messageContent.getId());
        }
        // 保存应用
        OperateResultWithData<BuildJob> resultWithData = this.save(buildJob);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionOrder = new RequisitionOrder();
            // 申请类型:发布申请
            requisitionOrder.setApplyType(ApplyType.DEPLOY);
            // 发布记录id
            requisitionOrder.setRelationId(buildJob.getId());
            // 申请摘要
            requisitionOrder.setSummary(buildJob.getAppName().concat("-").concat(buildJob.getAppName())
                    .concat("[").concat(buildJob.getName()).concat("]"));

            ResultData<RequisitionOrder> result = requisitionOrderService.createRequisition(requisitionOrder);
            if (result.successful()) {
                RequisitionOrder requisition = result.getData();
                BuildJobRequisitionDto dto = new BuildJobRequisitionDto();
                dto.setId(requisition.getId());
                dto.setApplicantAccount(requisition.getApplicantAccount());
                dto.setApplicantUserName(requisition.getApplicantUserName());
                dto.setApplicationTime(requisition.getApplicationTime());
                dto.setApplyType(requisition.getApplyType());
                dto.setApprovalStatus(requisition.getApprovalStatus());

                dto.setRelationId(buildJob.getId());
                dto.setEnvCode(buildJob.getEnvCode());
                dto.setEnvName(buildJob.getEnvName());
                dto.setAppId(buildJob.getAppId());
                dto.setAppName(buildJob.getAppName());
                dto.setGitId(buildJob.getGitId());
                dto.setModuleId(buildJob.getModuleId());
                dto.setModuleCode(buildJob.getModuleCode());
                dto.setModuleName(buildJob.getModuleName());
                dto.setRefTagId(buildJob.getRefTagId());
                dto.setRefTag(buildJob.getRefTag());
                dto.setName(buildJob.getName());
                dto.setRemark(content);
                dto.setExpCompleteTime(buildJob.getExpCompleteTime());
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
     * @param buildJob 应用
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<BuildJobRequisitionDto> modifyRequisition(BuildJob buildJob) {
        BuildJob entity = dao.findOne(buildJob.getId());
        if (Objects.isNull(entity)) {
            return ResultData.fail("应用不存在!");
        }
        // 检查应用审核状态
        if (!entity.getFrozen()) {
            return ResultData.fail("应用已审核,不允许编辑!");
        }
        // 通过模块和tag检查是否重复申请
        BuildJob existed = getByGitIdAndTag(buildJob.getGitId(), buildJob.getRefTag(), buildJob.getEnvCode(), buildJob.getType());
        if (Objects.nonNull(existed) && !StringUtils.equals(existed.getId(), entity.getId())) {
            return ResultData.fail("应用模块[" + buildJob.getModuleCode() + "]对应标签[" + buildJob.getRefTag() + "]存在申请记录,请不要重复申请.");
        }

        entity.setEnvCode(buildJob.getEnvCode());
        entity.setEnvName(buildJob.getEnvName());
        entity.setAppId(buildJob.getAppId());
        entity.setAppName(buildJob.getAppName());
        entity.setGitId(buildJob.getGitId());
        entity.setModuleId(buildJob.getModuleId());
        entity.setModuleCode(buildJob.getModuleCode());
        entity.setModuleName(buildJob.getModuleName());
        entity.setRefTagId(buildJob.getRefTagId());
        entity.setRefTag(buildJob.getRefTag());
        entity.setName(buildJob.getName());
        entity.setExpCompleteTime(buildJob.getExpCompleteTime());
        // 更新发版记录
        String content = buildJob.getRemark();
        if (StringUtils.isNotBlank(content)) {
            MessageContent messageContent;
            String messageId = entity.getMessageId();
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
            entity.setMessageId(messageContent.getId());
        }

        // 保存应用
        OperateResultWithData<BuildJob> resultWithData = this.save(entity);
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
            requisitionOrder.setApplyType(ApplyType.DEPLOY);
            // 发布记录id
            requisitionOrder.setRelationId(entity.getId());
            // 申请摘要
            requisitionOrder.setSummary(buildJob.getAppName().concat("-").concat(buildJob.getAppName())
                    .concat("[").concat(buildJob.getName()).concat("]"));

            ResultData<RequisitionOrder> result = requisitionOrderService.modifyRequisition(requisitionOrder);
            if (result.successful()) {
                RequisitionOrder requisition = result.getData();
                BuildJobRequisitionDto dto = new BuildJobRequisitionDto();
                dto.setId(requisition.getId());
                dto.setApplicantAccount(requisition.getApplicantAccount());
                dto.setApplicantUserName(requisition.getApplicantUserName());
                dto.setApplicationTime(requisition.getApplicationTime());
                dto.setApplyType(requisition.getApplyType());
                dto.setApprovalStatus(requisition.getApprovalStatus());
                dto.setRelationId(buildJob.getId());

                dto.setEnvCode(buildJob.getEnvCode());
                dto.setEnvName(buildJob.getEnvName());
                dto.setAppId(buildJob.getAppId());
                dto.setAppName(buildJob.getAppName());
                dto.setGitId(buildJob.getGitId());
                dto.setModuleId(buildJob.getModuleId());
                dto.setModuleCode(buildJob.getModuleCode());
                dto.setModuleName(buildJob.getModuleName());
                dto.setRefTagId(buildJob.getRefTagId());
                dto.setRefTag(buildJob.getRefTag());
                dto.setName(buildJob.getName());
                dto.setRemark(content);
                dto.setExpCompleteTime(buildJob.getExpCompleteTime());
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
        BuildJob releaseRecord = dao.findOne(id);
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
        BuildJob buildJob = dao.findOne(id);
        ResultData<BuildJob> resultData = build(buildJob, user.getAccount());
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }

        buildJob = resultData.getData();
        buildJob.setFrozen(Boolean.FALSE);
        OperateResultWithData<BuildJob> result = this.save(buildJob);
        if (result.successful()) {
            if (StringUtils.equals(TemplateType.DEPLOY.name(), buildJob.getType())) {
                // 禁用当前应用模块其他tag构建任务的构建
                dao.updateAllowBuildStatus(buildJob.getJobName(), TemplateType.DEPLOY.name(), Boolean.FALSE);
            }
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
        BuildJob buildJob = dao.findOne(id);
        ResultData<BuildJob> resultData = build(buildJob, user.getAccount());
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }

        BuildJob releaseRecord = resultData.getData();
        OperateResultWithData<BuildJob> result = this.save(releaseRecord);
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
    public ResultData<BuildDetailDto> getBuildDetail(String id) {
        BuildJob releaseRecord = dao.findOne(id);
        if (Objects.isNull(releaseRecord)) {
            return ResultData.fail("发布记录不存在");
        }

        String templateId;
        BuildDetailDto detailDto = modelMapper.map(releaseRecord, BuildDetailDto.class);
        if (StringUtils.equals(TemplateType.DEPLOY.name(), releaseRecord.getType())) {
            ResultData<DeployConfig> resultData = deployConfigService.getDeployConfig(releaseRecord.getEnvCode(), releaseRecord.getAppId(), releaseRecord.getModuleCode());
            if (resultData.failed()) {
                return ResultData.fail(resultData.getMessage());
            }
            DeployConfig config = resultData.getData();
            templateId = config.getTempId();
        } else {
            // 检查
            ResultData<DeployTemplate> resultData = templateService.getPublishTemplate(releaseRecord.getType());
            if (resultData.failed()) {
                return ResultData.fail(resultData.getMessage());
            }
            // @see templateService#syncJenkinsJob
            DeployTemplate deployTemplate = resultData.getData();
            templateId = deployTemplate.getId();
        }
        ResultData<List<DeployTemplateStageResponse>> result = deployTemplateStageService.getStageByTemplateId(templateId);
        detailDto.setStages(result.getData());

        if (BuildStatus.BUILDING != releaseRecord.getBuildStatus()) {
            Search search = Search.createSearch();
            search.addFilter(new SearchFilter(BuildDetail.FIELD_RECORD_ID, id));
            search.addFilter(new SearchFilter(BuildDetail.FIELD_BUILD_NUMBER, releaseRecord.getBuildNumber()));
            BuildDetail detail = buildDetailDao.findFirstByFilters(search);
            if (Objects.nonNull(detail)) {
                detailDto.setBuildLog(detail.getBuildLog());
            }
        }
        return ResultData.success(detailDto);
    }

    /**
     * 构建
     *
     * @param buildJob 发布记录Id
     * @return 返回发布记录
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<BuildJob> build(BuildJob buildJob, String account) {
        if (Objects.nonNull(buildJob)) {
            // 检查是否允许构建
            if (!buildJob.getAllowBuild()) {
                return ResultData.fail("模块[" + buildJob.getModuleCode() + "]已有新的构建任务,请使用新任务构建.");
            }

            final String jobName;
            final boolean needRelease;
            if (StringUtils.equals(TemplateType.DEPLOY.name(), buildJob.getType())) {
                // 检查部署配置是否存在
                ResultData<DeployConfig> resultData = deployConfigService.getDeployConfig(buildJob.getEnvCode(), buildJob.getAppId(), buildJob.getModuleCode());
                if (resultData.failed()) {
                    return ResultData.fail(resultData.getMessage());
                }
                jobName = buildJob.getJobName();
                needRelease = false;
            } else {
                // 检查
                ResultData<DeployTemplate> resultData = templateService.getPublishTemplate(buildJob.getType());
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
            params.put(Constants.DEPLOY_PARAM_PROJECT_NAME, buildJob.getModuleCode());
            // 参数:应用git仓库地址
            AppModule module = moduleService.getAppModuleByGitId(buildJob.getGitId());
            params.put(Constants.DEPLOY_PARAM_GIT_PATH, Objects.isNull(module) ? "null" : module.getGitHttpUrl());
            // 参数:代码分支或者TAG
            params.put(Constants.DEPLOY_PARAM_BRANCH, buildJob.getRefTag());

            // 调用Jenkins构建
            ResultData<Integer> buildResult = jenkinsService.buildJob(jobName, params);
            if (buildResult.successful()) {
                final int buildNumber = buildResult.getData();
                buildJob.setBuildNumber(buildNumber);
                // 更新构建状态为构建中
                buildJob.setBuildStatus(BuildStatus.BUILDING);

                // 异步上传
                CompletableFuture.runAsync(() -> ContextUtil.getBean(BuildJobService.class).runBuild(buildJob.getId(), jobName, buildNumber, account, needRelease));
            } else {
                int buildNumber = Objects.isNull(buildJob.getBuildNumber()) ? 0 : buildJob.getBuildNumber();
                buildJob.setBuildNumber(buildNumber);
                buildJob.setBuildStatus(BuildStatus.FAILURE);
                buildJob.setAllowBuild(Boolean.TRUE);

                BuildDetail detail = new BuildDetail();
                detail.setRecordId(buildJob.getId());
                detail.setJobName(jobName);
                detail.setBuildNumber(buildNumber);
                detail.setBuildAccount(account);
                detail.setBuildLog(buildResult.getMessage());
                detail.setStartTime(System.currentTimeMillis());
                detail.setBuildStatus(BuildStatus.FAILURE);
                buildDetailDao.save(detail);
            }
            // 构建时间
            buildJob.setBuildTime(LocalDateTime.now());
            // 构建人账号
            buildJob.setBuildAccount(account);
            return ResultData.success(buildJob);
        } else {
            return ResultData.fail("构建任务不存在");
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
        BuildDetail detail = new BuildDetail();
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

        BuildJob record = dao.findOne(id);
        if (Objects.nonNull(record)) {
            BuildStatus status = detail.getBuildStatus();
            // 更新发布记录状态
            record.setBuildStatus(status);
            if (BuildStatus.SUCCESS != status) {
                record.setAllowBuild(Boolean.TRUE);
            }
            dao.save(record);
            if (needRelease) {
                releaseVersionService.releaseVersion(record);
            }
        }
    }

    /**
     * 通过git和tag获取构建记录
     *
     * @param gitId gitId
     * @param tag   tag
     * @return 构建记录
     */
    public BuildJob getByGitIdAndTag(String gitId, String tag, String envCode, String type) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(BuildJob.FIELD_GIT_ID, gitId));
        search.addFilter(new SearchFilter(BuildJob.FIELD_TAG_NAME, tag));
        search.addFilter(new SearchFilter(BuildJob.FIELD_ENV_CODE, envCode));
        search.addFilter(new SearchFilter(BuildJob.FIELD_TYPE, type));
        return dao.findFirstByFilters(search);
    }

    /**
     * Gitlab Push Hook
     *
     * @param request gitlab push hook
     * @return 返回结果
     */
    //@Transactional
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
        ResultData<Void> resultData = buildModule(module, "dev", "开发环境", request.getUserUsername());
        if (resultData.failed()) {
            LOG.error("{} 的Push Hook 异常{}", request, resultData.getMessage());
        }
        return resultData;
    }

    /**
     * 按模块进行构建
     *
     * @param module       模块
     * @param buildAccount 构建人账号
     * @return 构建结果
     */
    @Transactional
    public ResultData<Void> buildModule(AppModule module, String envCode, String envName, String buildAccount) {
        TemplateType type = TemplateType.DEPLOY;
        String tag = "dev";
        try {
            BuildJob record = getByGitIdAndTag(module.getGitId(), tag, envCode, type.name());
            if (Objects.isNull(record)) {
                record = new BuildJob();
                record.setType(type.name());
                record.setEnvCode(envCode);
                record.setEnvName(envName);
                record.setAppId(module.getAppId());
                record.setAppName(module.getAppName());
                record.setGitId(module.getGitId());
                record.setModuleId(module.getId());
                record.setModuleCode(module.getCode());
                record.setModuleName(module.getName());
                record.setRefTagId(tag);
                record.setRefTag(tag);
                record.setName("开发构建-" + module.getName());
                record.setFrozen(Boolean.FALSE);
                record.setExpCompleteTime(LocalDateTime.now());
                this.save(record);
            }
            ResultData<BuildJob> resultData = this.build(record, buildAccount);
            if (resultData.successful()) {
                this.save(resultData.getData());
                return ResultData.success();
            } else {
                return ResultData.fail(resultData.getMessage());
            }
        } catch (Exception e) {
            String error = ExceptionUtils.getRootCauseMessage(e);
            emailManager.sendMail(managerName + "-Push Hook 异常", error, buildAccount);
            return ResultData.fail(error);
        }
    }

    /**
     * 根据环境代码和应用模块id获取部署的tag
     *
     * @param envCode  环境代码
     * @param moduleId 应用模块id
     * @return 发挥tagName
     */
    public String getLastTagName(String envCode, String moduleId) {
        return dao.getLastTag(envCode, moduleId);
    }

    /**
     * 根据环境代码和应用模块id获取部署的tag与指定tag的变化记录
     *
     * @param envCode    环境代码
     * @param moduleId   应用模块id
     * @param currentTag 指定tag
     * @return 发挥tagName
     */
    public List<Tag> getTags(String envCode, String moduleId, String currentTag) {
        // 获取当前指定环境部署的tag
        String lastTag = dao.getLastTag(envCode, moduleId);

        Search search = Search.createSearch();
        if (StringUtils.isNotBlank(lastTag)) {
            search.addFilter(new SearchFilter(Tag.FIELD_CODE, lastTag, SearchFilter.Operator.GT));
        }
        search.addFilter(new SearchFilter(Tag.FIELD_CODE, currentTag, SearchFilter.Operator.LE));
        search.addFilter(new SearchFilter(Tag.FIELD_MODULE_ID, moduleId));
        search.addSortOrder(new SearchOrder(Tag.FIELD_MAJOR, SearchOrder.Direction.DESC));
        search.addSortOrder(new SearchOrder(Tag.FIELD_MINOR, SearchOrder.Direction.DESC));
        search.addSortOrder(new SearchOrder(Tag.FIELD_REVISED, SearchOrder.Direction.DESC));
        return tagService.findByFilters(search);
    }
}
