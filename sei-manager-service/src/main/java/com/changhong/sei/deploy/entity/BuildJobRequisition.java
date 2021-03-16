package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.deploy.dto.ApplyType;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 构建任务申请单(BuildJobRequisition)实体类
 *
 * @author sei
 * @since 2020-11-26 14:45:20
 */
@Entity
@Table(name = "v_build_job_requisition")
@DynamicInsert
@DynamicUpdate
public class BuildJobRequisition extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -78400014111481829L;
    public static final String APPLICANT_ACCOUNT = "applicantAccount";
    /**
     * 业务key
     */
    @Column(name = "biz_key")
    private String bizKey;
    /**
     * 关联id
     *
     * @see ApplyType
     */
    @Column(name = "relation_id")
    private String relationId;
    /**
     * 申请人账号
     */
    @Column(name = "applicant_account")
    private String applicantAccount;
    /**
     * 申请人名称
     */
    @Column(name = "applicant_user_name")
    private String applicantUserName;
    /**
     * 申请时间
     */
    @Column(name = "application_time")
    private LocalDateTime applicationTime;
    /**
     * 审核状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;
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
     * 模块名称
     */
    @Column(name = "module_code")
    private String moduleCode;
    /**
     * 模块名称
     */
    @Column(name = "module_name")
    private String moduleName;
    /**
     * 标签名称
     */
    @Column(name = "tag_name")
    private String tagName;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 构建状态
     */
    @Column(name = "build_status")
    private String buildStatus;
    /**
     * 期望完成时间
     */
    @Column(name = "exp_complete_time")
    private LocalDateTime expCompleteTime;

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getApplicantAccount() {
        return applicantAccount;
    }

    public void setApplicantAccount(String applicantAccount) {
        this.applicantAccount = applicantAccount;
    }

    public String getApplicantUserName() {
        return applicantUserName;
    }

    public void setApplicantUserName(String applicantUserName) {
        this.applicantUserName = applicantUserName;
    }

    public LocalDateTime getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(LocalDateTime applicationTime) {
        this.applicationTime = applicationTime;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
    }

    public LocalDateTime getExpCompleteTime() {
        return expCompleteTime;
    }

    public void setExpCompleteTime(LocalDateTime expCompleteTime) {
        this.expCompleteTime = expCompleteTime;
    }
}