package com.changhong.sei.deploy.dto;

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
     * 模板全局参数
     */
    @ApiModelProperty(value = "模板全局参数")
    private String globalParam;
    /**
     * 是否冻结
     */
    @ApiModelProperty(value = "是否冻结")
    private Boolean frozen = Boolean.FALSE;
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

    public String getGlobalParam() {
        return globalParam;
    }

    public void setGlobalParam(String globalParam) {
        this.globalParam = globalParam;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}