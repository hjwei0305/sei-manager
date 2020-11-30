package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * 申请记录(Requisition)DTO类
 *
 * @author sei
 * @since 2020-11-26 14:45:23
 */
@ApiModel(description = "申请记录DTO")
public class RequisitionDto extends BaseEntityDto {
    private static final long serialVersionUID = -38024625578859016L;

    /**
     * 关联id
     *
     * @see ApplyType
     */
    @ApiModelProperty(value = "关联id")
    private String relationId;
    /**
     * 流程实例id
     */
    @ApiModelProperty(value = "流程实例id")
    private String flowInstanceId;
    /**
     * 流程类型名称
     */
    @ApiModelProperty(value = "流程类型名称")
    private String flowTypeName;
    /**
     * 任务号
     * 用于需多人处理的事项
     */
    @ApiModelProperty(value = "任务号")
    private String taskNo;
    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String taskName;
    /**
     * 申请人账号
     */
    @ApiModelProperty(value = "申请人账号")
    private String applicantAccount;
    /**
     * 申请人名称
     */
    @ApiModelProperty(value = "申请人名称")
    private String applicantUserName;
    /**
     * 申请类型
     */
//    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty(value = "申请类型")
    private ApplyType applyType;
    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间", example = "2020-01-14 22:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applicationTime;
    /**
     * 审核状态
     */
//    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty(value = "审核状态")
    private ApprovalStatus approvalStatus = ApprovalStatus.INITIAL;
//    /**
//     * 处理人账号
//     */
//    @ApiModelProperty(value = "处理人账号")
//    private String handleAccount;
//    /**
//     * 处理人
//     */
//    @ApiModelProperty(value = "处理人")
//    private String handleUserName;
//    /**
//     * 处理日志
//     */
//    @ApiModelProperty(value = "处理日志")
//    private String handleLog;
//    /**
//     * 处理时间
//     */
//    @ApiModelProperty(value = "处理时间", example = "2020-01-14 22:18:48")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime handleTime;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getFlowInstanceId() {
        return flowInstanceId;
    }

    public void setFlowInstanceId(String flowInstanceId) {
        this.flowInstanceId = flowInstanceId;
    }

    public String getFlowTypeName() {
        return flowTypeName;
    }

    public void setFlowTypeName(String flowTypeName) {
        this.flowTypeName = flowTypeName;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public void setApplyType(ApplyType applyType) {
        this.applyType = applyType;
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