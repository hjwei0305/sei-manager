package com.changhong.sei.cicd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 部署阶段(DeployStage)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:02
 */
@ApiModel(description = "部署阶段参数DTO")
public class DeployStageParamDto implements Serializable {
    private static final long serialVersionUID = -50461186309148987L;
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
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 默认值
     */
    @ApiModelProperty(value = "默认值")
    private String defaultValue = "";
    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private String value;

    private boolean trim = Boolean.FALSE;

    public DeployStageParamDto() {

    }

    public DeployStageParamDto(String code, String name) {
        this.code = code;
        this.name = name;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }
}