package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 流程实例任务节点(FlowInstanceTask)DTO类
 *
 * @author sei
 * @since 2020-12-16 10:53:47
 */
@ApiModel(description = "流程实例任务节点DTO")
public class FlowInstanceTaskDto extends BaseEntityDto {
    private static final long serialVersionUID = 126968462254321671L;

    /**
     * 流程实例ID
     */
    @NotBlank
    @ApiModelProperty(value = "流程类型id")
    private String instanceId;
    /**
     * 代码
     */
    @NotBlank
    @ApiModelProperty(value = "任务节点号")
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @ApiModelProperty(value = "任务节点名称")
    private String name;
    /**
     * 处理人账号
     */
    @NotBlank
    @ApiModelProperty(value = "处理人账号")
    private String handleAccount;
    /**
     * 处理人
     */
    @NotBlank
    @ApiModelProperty(value = "处理人")
    private String handleUserName;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}