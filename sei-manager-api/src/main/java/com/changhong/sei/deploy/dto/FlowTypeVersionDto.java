package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 流程类型版本(FlowDeTypeVersion)DTO类
 *
 * @author sei
 * @since 2020-12-16 10:53:49
 */
@ApiModel(description = "流程类型版本DTO")
public class FlowTypeVersionDto {
    private static final long serialVersionUID = -13224391892073412L;
    /**
     * 流程类型id
     */
    @ApiModelProperty(value = "流程类型id")
    private String typeId;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Object version;
//    /**
//     * 名称
//     */
//    @ApiModelProperty(value = "名称")
//    private String name;
//    /**
//     * 描述说明
//     */
//    @ApiModelProperty(value = "描述说明")
//    private String remark;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }

}