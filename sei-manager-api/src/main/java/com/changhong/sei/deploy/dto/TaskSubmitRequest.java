package com.changhong.sei.deploy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 实现功能：提交申请单
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-27 18:05
 */
@ApiModel(description = "提交申请单请求")
public class TaskSubmitRequest implements Serializable {
    private static final long serialVersionUID = -5619138703809373270L;

    /**
     * 申请单id
     */
    @NotBlank
    @ApiModelProperty(notes = "申请单id")
    private String requisitionId;
    /**
     * 流程类型id
     */
    @NotBlank
    @ApiModelProperty(notes = "流程类型id")
    private String flowTypeId;
    /**
     * 流程类型名称
     */
    @NotBlank
    @ApiModelProperty(notes = "流程类型名称")
    private String flowTypeName;

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

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
}
