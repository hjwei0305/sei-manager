package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部署模板参数(DeployTemplateParam)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:06
 */
@ApiModel(description = "部署模板参数DTO")
public class DeployTemplateParamDto extends BaseEntityDto {
    private static final long serialVersionUID = -12278177993316962L;
    /**
     * 模版id
     */
    @ApiModelProperty(value = "模版id")
    private String templateId;
    /**
     * 阶段id
     */
    @ApiModelProperty(value = "阶段id")
    private String stageId;
    /**
     * 参数代码
     */
    @ApiModelProperty(value = "参数代码")
    private String code;
    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    private String name;
    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private String value;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;


    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}