package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.deploy.dto.ApplyType;
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
@Table(name = "cicd_requisition_order")
@DynamicInsert
@DynamicUpdate
public class RequisitionOrder extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 8488019354515570494L;

    public static final String FIELD_RELATION_ID = "relationId";
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
     * 摘要
     */
    @Column(name = "summary")
    private String summary;
    /**
     * 申请类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "application_type")
    private ApplyType applyType;
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
    private ApprovalStatus approvalStatus = ApprovalStatus.INITIAL;

    /**
     * 流程实例id
     */
    @Column(name = "flow_instance_id")
    private String flowInstanceId;
    /**
     * 流程类型名称
     */
    @Column(name = "flow_type_name")
    private String flowTypeName;
    /**
     * 流程版本
     */
    @Column(name = "flow_version")
    private Integer flowVersion;
    /**
     * 版本乐观锁
     */
    @Version
    @Column(name = "version_")
    private Long version = 0L;

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

    public ApplyType getApplyType() {
        return applyType;
    }

    public void setApplyType(ApplyType applicationType) {
        this.applyType = applicationType;
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

    public String getFlowInstanceId() {
        return flowInstanceId;
    }

    public void setFlowInstanceId(String flowInstanceId) {
        this.flowInstanceId = flowInstanceId;
    }

    public Integer getFlowVersion() {
        return flowVersion;
    }

    public void setFlowVersion(Integer flowVersion) {
        this.flowVersion = flowVersion;
    }

    public String getFlowTypeName() {
        return flowTypeName;
    }

    public void setFlowTypeName(String flowTypeName) {
        this.flowTypeName = flowTypeName;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
