package com.changhong.sei.cicd.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.entity.IFrozen;
import com.changhong.sei.cicd.dto.BuildStatus;
import com.changhong.sei.cicd.dto.TemplateType;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 构建任务(BuildJob)实体类
 *
 * @author sei
 * @since 2020-11-26 14:45:20
 */
@Entity
@Table(name = "cicd_build_job")
@DynamicInsert
@DynamicUpdate
public class BuildJob extends BaseEntity implements IFrozen, Serializable {
    private static final long serialVersionUID = -78400014111481829L;
    public static final String FIELD_GIT_ID = "gitId";
    public static final String FIELD_TAG_NAME = "refTag";
    public static final String FIELD_MODULE_ID = "moduleId";
    public static final String FIELD_MODULE_CODE = "moduleCode";
    /**
     * 类型
     */
    @Column(name = "type")
    private String type = TemplateType.DEPLOY.name();
    /**
     * 任务名称
     */
    @Column(name = "job_name")
    private String jobName;
    /**
     * 环境
     */
    @Column(name = "env_code")
    private String envCode;
    /**
     * 环境
     */
    @Column(name = "env_name")
    private String envName;
    /**
     * 所属应用id
     */
    @Column(name = "app_id")
    private String appId;
    /**
     * 所属应用id
     */
    @Column(name = "app_name")
    private String appName;
    /**
     * 模块git id
     */
    @Column(name = "git_id")
    private String gitId;
    /**
     * 模块id
     */
    @Column(name = "module_id")
    private String moduleId;
    /**
     * 模块代码
     */
    @Column(name = "module_code")
    private String moduleCode;
    /**
     * 模块名称
     */
    @Column(name = "module_name")
    private String moduleName;
    /**
     * 引用tagId
     */
    @Column(name = "ref_tag_id")
    private String refTagId;
    /**
     * 引用tag
     */
    @Column(name = "ref_tag")
    private String refTag;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 描述说明id
     */
    @Column(name = "message_id")
    private String messageId;

    /**
     * 是否冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.TRUE;
    /**
     * 期望完成时间
     */
    @Column(name = "exp_complete_time")
    private LocalDateTime expCompleteTime;
    /**
     * Jenkins构建号
     */
    @Column(name = "build_number")
    private Integer buildNumber;
    /**
     * Jenkins构建状态
     */
    @Column(name = "build_status")
    @Enumerated(EnumType.STRING)
    private BuildStatus buildStatus = BuildStatus.NOT_BUILT;
    /**
     * Jenkins构建时间
     */
    @Column(name = "build_time")
    private LocalDateTime buildTime;
    /**
     * Jenkins构建人账号
     */
    @Column(name = "build_account")
    private String buildAccount;
    /**
     * 是否允许构建
     */
    @Column(name = "allow_build")
    private Boolean allowBuild = Boolean.TRUE;

    @Transient
    private String remark;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 环境代码+模块代码
     *
     * @return Jenkins任务名
     */
    public String getJobName() {
        if (StringUtils.isBlank(jobName)) {
            jobName = getEnvCode() + "_" + getModuleCode();
        }
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getRefTagId() {
        return refTagId;
    }

    public void setRefTagId(String refTagId) {
        this.refTagId = refTagId;
    }

    public String getRefTag() {
        return refTag;
    }

    public void setRefTag(String refTag) {
        this.refTag = refTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    @Override
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public LocalDateTime getExpCompleteTime() {
        return expCompleteTime;
    }

    public void setExpCompleteTime(LocalDateTime expCompleteTime) {
        this.expCompleteTime = expCompleteTime;
    }

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }

    public LocalDateTime getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(LocalDateTime buildTime) {
        this.buildTime = buildTime;
    }

    public String getBuildAccount() {
        return buildAccount;
    }

    public void setBuildAccount(String buildAccount) {
        this.buildAccount = buildAccount;
    }

    public Boolean getAllowBuild() {
        return allowBuild;
    }

    public void setAllowBuild(Boolean allowBuild) {
        this.allowBuild = allowBuild;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}