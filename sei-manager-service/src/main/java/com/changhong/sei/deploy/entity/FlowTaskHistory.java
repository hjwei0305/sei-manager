package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.deploy.dto.ApplicationType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能： 任务执行历史
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 19:04
 */
@Entity
@Table(name = "flow_task_history")
@DynamicInsert
@DynamicUpdate
public class FlowTaskHistory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8488019354515570494L;

    /**
     * 申请单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 关联id
     *
     * @see ApplicationType
     */
    @Column(name = "relation_id")
    private String relationId;
    /**
     * 申请类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "application_type")
    private ApplicationType applicationType;
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
     * 处理人账号
     */
    @Column(name = "execute_account")
    private String executeAccount;
    /**
     * 处理人
     */
    @Column(name = "execute_user_name")
    private String executeUserName;
    /**
     * 处理结果
     */
    @Column(name = "result")
    private String result;
    /**
     * 处理时间
     */
    @Column(name = "execute_time")
    private LocalDateTime executeTime;

    public FlowTaskHistory() {
    }

    public FlowTaskHistory(FlowTaskInstance instance) {
        this.orderId = instance.getOrderId();
        this.relationId = instance.getRelationId();
        this.applicationType = instance.getApplicationType();
        this.flowInstanceId = instance.getFlowInstanceId();
        this.flowTypeName = instance.getFlowTypeName();
        this.taskNo = instance.getTaskNo();
        this.taskName = instance.getTaskName();
        this.initiatorAccount = instance.getInitiatorAccount();
        this.initiatorUserName = instance.getInitiatorUserName();
        this.initTime = instance.getInitTime();
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

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public String getFlowTypeName() {
        return flowTypeName;
    }

    public void setFlowTypeName(String flowTypeName) {
        this.flowTypeName = flowTypeName;
    }

    public String getFlowInstanceId() {
        return flowInstanceId;
    }

    public void setFlowInstanceId(String flowInstanceId) {
        this.flowInstanceId = flowInstanceId;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(LocalDateTime executeTime) {
        this.executeTime = executeTime;
    }
}
