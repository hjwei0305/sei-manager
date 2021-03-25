package com.changhong.sei.cicd.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.cicd.dto.ApplyType;
import com.changhong.sei.cicd.dto.ApprovalStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 版本发布记录(ReleaseVersion)(视图)
 *
 * @author sei
 * @since 2020-11-26 14:45:20
 */
@Entity
@Table(name = "v_version_record_requisition")
public class VersionRecordRequisition extends BaseEntity implements Serializable {
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
     * 引用tag
     */
    @Column(name = "ref_tag_id")
    private String refTagId;
    /**
     * 引用tag
     */
    @Column(name = "ref_tag")
    private String refTag;
    /**
     * 版本号
     */
    @Column(name = "version_")
    private String version;

    /**
     * 版本名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 构建状态
     */
    @Column(name = "build_status")
    private String buildStatus;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
}