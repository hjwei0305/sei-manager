package com.changhong.sei.cicd.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能：待办任务
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-30 15:14
 */
@ApiModel(description = "待办任务")
public class FlowToDoTaskDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 7058966901999937565L;

    /**
     * 流程类型代码
     */
    @ApiModelProperty(notes = "流程类型代码")
    private String flowTypeCode;
    /**
     * 流程类型名称
     */
    @ApiModelProperty(notes = "流程类型名称")
    private String flowTypeName;
    /**
     * 任务号(序号)
     */
    @ApiModelProperty(notes = "任务号(序号)")
    private Integer taskNo;
    /**
     * 任务名称
     */
    @ApiModelProperty(notes = "任务名称")
    private String taskName;
    /**
     * 发起人账号
     */
    @ApiModelProperty(notes = "发起人账号")
    private String initiatorAccount;
    /**
     * 发起人
     */
    @ApiModelProperty(notes = "发起人")
    private String initiatorUserName;
    /**
     * 发起时间
     */
    @ApiModelProperty(notes = "发起时间", example = "2020-01-14 22:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime initTime;
    /**
     * 待处理人账号
     */
    @ApiModelProperty(notes = "待处理人账号")
    private String executeAccount;
    /**
     * 待处理人
     */
    @ApiModelProperty(notes = "待处理人")
    private String executeUserName;

    /**
     * 申请单id
     */
    @ApiModelProperty(notes = "申请单id")
    private String orderId;

    /**
     * 关联id
     */
    @ApiModelProperty(notes = "关联id")
    private String relationId;
    /**
     * 申请类型
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty(notes = "申请类型")
    private ApplyType applyType;

    /**
     * 摘要
     */
    @ApiModelProperty(notes = "摘要")
    private String summary;
    /**
     * 待处理
     */
    @ApiModelProperty(notes = "待处理")
    private Boolean pending = Boolean.TRUE;

    public String getFlowTypeCode() {
        return flowTypeCode;
    }

    public void setFlowTypeCode(String flowTypeCode) {
        this.flowTypeCode = flowTypeCode;
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
