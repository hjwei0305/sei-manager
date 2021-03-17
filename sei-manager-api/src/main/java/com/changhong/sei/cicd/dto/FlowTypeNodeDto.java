package com.changhong.sei.cicd.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 流程类型节点定义(FlowTypeNode)DTO类
 *
 * @author sei
 * @since 2020-12-16 10:53:45
 */
@ApiModel(description = "流程类型节点定义DTO")
public class FlowTypeNodeDto extends BaseEntityDto {
    private static final long serialVersionUID = -76243251130817703L;
    /**
     * 流程类型id
     */
    @NotBlank
    @ApiModelProperty(value = "流程类型id", required = true)
    private String typeId;
    /**
     * 节点序号
     */
    @NotBlank
    @ApiModelProperty(value = "节点序号(0-9999)", required = true)
    private String code;
    /**
     * 节点名称
     */
    @NotBlank
    @ApiModelProperty(value = "节点名称", required = true)
    private String name;
    /**
     * 处理人账号
     */
    @NotBlank
    @ApiModelProperty(value = "处理人账号", required = true)
    private String handleAccount;
    /**
     * 处理人
     */
    @NotBlank
    @ApiModelProperty(value = "处理人", required = true)
    private String handleUserName;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;


    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}