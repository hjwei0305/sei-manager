package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.deploy.dto.ApplicationType;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能： 审核记录
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 19:04
 */
@Entity
@Table(name = "approval_record")
@DynamicInsert
@DynamicUpdate
public class ApprovalRecord extends BaseEntity implements Serializable {
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
     * 处理人账号
     */
    @Column(name = "handle_account")
    private String handleAccount;
    /**
     * 处理人
     */
    @Column(name = "handle_user_name")
    private String handleUserName;
    /**
     * 处理日志
     */
    @Column(name = "handle_log")
    private String handleLog;
    /**
     * 处理时间
     */
    @Column(name = "handle_time")
    private LocalDateTime handleTime;

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

    public String getHandleAccount() {
        return handleAccount;
    }

    public void setHandleAccount(String handleAccount) {
        this.handleAccount = handleAccount;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public String getHandleLog() {
        return handleLog;
    }

    public void setHandleLog(String handleLog) {
        this.handleLog = handleLog;
    }

    public LocalDateTime getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(LocalDateTime handleTime) {
        this.handleTime = handleTime;
    }
}
