package com.changhong.sei.cicd.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.cicd.dto.ApplyType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能： 待办任务
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-30 16:06
 */
@Entity
@Table(name = "flow_todo_task")
public class FlowToDoTask extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4502581813571547474L;
    public static final String FIELD_EXECUTE_ACCOUNT = "executeAccount";
    public static final String FIELD_PENDING = "pending";
    public static final String FIELD_APPLY_TYPE = "applyType";
    public static final String FIELD_ORDER_ID = "orderId";

    /**
     * 流程类型代码
     */
    @Column(name = "flow_type_code")
    private String flowTypeCode;
    /**
     * 流程类型名称
     */
    @Column(name = "flow_type_name")
    private String flowTypeName;
    /**
     * 任务号(序号)
     */
    @Column(name = "task_no")
    private Integer taskNo;
    /**
     * 任务名称
     */
    @Column(name = "task_name")
    private String taskName;
    /**
     * 发起人账号
     */
    @Column(name = "initiator_account")
    private String initiatorAccount;
    /**
     * 发起人
     */
    @Column(name = "initiator_user_name")
    private String initiatorUserName;
    /**
     * 发起时间
     */
    @Column(name = "init_time")
    private LocalDateTime initTime;
    /**
     * 待处理人账号
     */
    @Column(name = "execute_account")
    private String executeAccount;
    /**
     * 待处理人
     */
    @Column(name = "execute_user_name")
    private String executeUserName;

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
     * 待处理
     */
    @Column(name = "is_pending")
    private Boolean pending = Boolean.TRUE;

    public String getFlowTypeCode() {
        return flowTypeCode;
    }

    public void setFlowTypeCode(String flowTypeId) {
        this.flowTypeCode = flowTypeId;
    }

    public String getFlowTypeName() {
        return flowTypeName;
    }

    public void setFlowTypeName(String flowTypeName) {
        this.flowTypeName = flowTypeName;
    }

    public Integer getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(Integer taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getInitiatorAccount() {
        return initiatorAccount;
    }

    public void setInitiatorAccount(String initiatorAccount) {
        this.initiatorAccount = initiatorAccount;
    }

    public String getInitiatorUserName() {
        return initiatorUserName;
    }

    public void setInitiatorUserName(String initiatorUserName) {
        this.initiatorUserName = initiatorUserName;
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    public void setInitTime(LocalDateTime initTime) {
        this.initTime = initTime;
    }

    public String getExecuteAccount() {
        return executeAccount;
    }

    public void setExecuteAccount(String executeAccount) {
        this.executeAccount = executeAccount;
    }

    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

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

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }
}
