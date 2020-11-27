package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.deploy.dto.ApplicationType;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能： 申请记录
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 19:04
 */
@Entity
@Table(name = "requisition_order")
@DynamicInsert
@DynamicUpdate
public class RequisitionOrder extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 8488019354515570494L;

    /**
     * 关联id
     *
     * @see ApplicationType
     */
    @Column(name = "relation_id")
    private String relationId;
    /**
     * 摘要
     */
    @Column(name = "summary")
    private String summary;
    /**
     * 申请类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "application_type")
    private ApplicationType applicationType;
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
    private ApprovalStatus approvalStatus = ApprovalStatus.initial;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
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

}
