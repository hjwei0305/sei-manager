package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 实现功能：审核申请单请求
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-27 18:05
 */
@ApiModel(description = "审核申请单请求")
public class TaskHandleRequest implements Serializable {
    private static final long serialVersionUID = -5619138703809373270L;
    /**
     * 流程任务id
     * taskInstanceId
     */
    @ApiModelProperty(notes = "任务实例id")
    private String taskId;
    /**
     * 申请单id
     */
    @NotBlank
    @ApiModelProperty(notes = "申请单id")
    private String requisitionId;
    /**
     * 审核操作类型
     */
    @NotBlank
    @ApiModelProperty(notes = "审核操作类型")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private OperationType operationType;
    @NotBlank
    @ApiModelProperty(notes = "驳回处理意见")
    private String message;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
