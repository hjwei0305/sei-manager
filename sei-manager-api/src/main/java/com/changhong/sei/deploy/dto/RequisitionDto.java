package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

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