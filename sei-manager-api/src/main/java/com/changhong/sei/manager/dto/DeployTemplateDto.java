package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部署模板(DeployTemplate)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:04
 */
@ApiModel(description = "部署模板DTO")
public class DeployTemplateDto extends BaseEntityDto {
    private static final long serialVersionUID = -72966842496774043L;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    private String name;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}