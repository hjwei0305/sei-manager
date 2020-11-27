package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * 申请记录(RequisitionRecord)DTO类
 *
 * @author sei
 * @since 2020-11-26 14:45:23
 */
@ApiModel(description = "申请记录DTO")
public class RequisitionRecordDto extends BaseEntityDto {
    private static final long serialVersionUID = -38024625578859016L;

    /**
     * 关联id
     *
     * @see ApplicationType
     */
    @ApiModelProperty(value = "关联id")
    private String relationId;
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
    @ApiModelProperty(value = "申请类型")
    private ApplicationType applicationType;
    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applicationTime;
    /**
     * 是否已处理
     */
    @ApiModelProperty(value = "是否已处理")
    private Boolean handled = Boolean.FALSE;
    /**
     * 处理人账号
     */
    @ApiModelProperty(value = "处理人账号")
    private String handleAccount;
    /**
     * 处理人
     */
    @ApiModelProperty(value = "处理人")
    private String handleUserName;
    /**
     * 处理日志
     */
    @ApiModelProperty(value = "处理日志")
    private String handleLog;
    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime handleTime;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
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

    public Boolean getHandled() {
        return handled;
    }

    public void setHandled(Boolean handled) {
        this.handled = handled;
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