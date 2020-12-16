package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 流程类型节点记录(FlowDeTypeNodeRecord)DTO类
 *
 * @author sei
 * @since 2020-12-16 10:53:47
 */
@ApiModel(description = "流程类型节点记录DTO")
public class FlowTypeNodeRecordDto extends BaseEntityDto {
    private static final long serialVersionUID = 126968462254321671L;
    /**
     * 流程类型id
     */
    @ApiModelProperty(value = "流程类型id")
    private String typeId;
    @ApiModelProperty(value = "流程类型")
    private String typeName;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Object version;
    /**
     * 节点序号
     */
    @ApiModelProperty(value = "节点序号")
    private Integer code;
    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    private String name;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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