package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 流程类型(FlowDeType)DTO类
 *
 * @author sei
 * @since 2020-12-16 10:53:38
 */
@ApiModel(description = "流程类型DTO")
public class FlowTypeDto extends BaseEntityDto {
    private static final long serialVersionUID = 887727657051875692L;
    /**
     * 名称
     */
    @NotBlank
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer version;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 冻结
     */
    @ApiModelProperty(value = "冻结")
    private Boolean frozen = Boolean.FALSE;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

}