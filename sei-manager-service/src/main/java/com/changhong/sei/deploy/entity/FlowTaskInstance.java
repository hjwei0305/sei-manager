package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.deploy.dto.ApplyType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能：流程任务实例
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 19:04
 */
@Entity
@Table(name = "flow_task_instance")
@DynamicInsert
@DynamicUpdate
public class FlowTaskInstance extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8488019354515570494L;

    public static final String FIELD_ORDER_ID = "orderId";

    /**
     * 流程类型id
     */
    @Column(name = "flow_type_id")
    private String flowTypeId;
    /**
     * 流程类型名称
     */
    @Column(name = "flow_type_name")
    private String flowTypeName;
    /**
     * 流程类型版本
     */
    @Column(name = "version_")
    private Integer version;

    /**
     * 申请单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 关联id
     *
     * @see ApplyType
     */
    @Column(name = "relation_id")
    private String relationId;
    /**
     * 申请类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "apply_type")
    private ApplyType applyType;
    /**
     * 摘要
     */
    @Column(name = "summary")
    private String summary;
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

    public String getFlowTypeId() {
        return flowTypeId;
    }

    public void setFlowTypeId(String flowTypeId) {
        this.flowTypeId = flowTypeId;
    }

    public String getFlowTypeName() {
        return flowTypeName;
    }

    public void setFlowTypeName(String flowTypeName) {
        this.flowTypeName = flowTypeName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

//    public Integer getTaskNo() {
//        return taskNo;
//    }
//
//    public void setTaskNo(Integer taskNo) {
//        this.taskNo = taskNo;
//    }
//
//    public String getTaskName() {
//        return taskName;
//    }
//
//    public void setTaskName(String taskName) {
//        this.taskName = taskName;
//    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public ApplyType getApplyType() {
        return applyType;
    }

    public void setApplyType(ApplyType applyType) {
        this.applyType = applyType;
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

    public LocalDateTime getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(LocalDateTime applicationTime) {
        this.applicationTime = applicationTime;
    }
}
