package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部署参数(DeployParam)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:00
 */
@ApiModel(description = "部署参数DTO")
public class DeployParamDto extends BaseEntityDto {
    private static final long serialVersionUID = 907165001378309821L;
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