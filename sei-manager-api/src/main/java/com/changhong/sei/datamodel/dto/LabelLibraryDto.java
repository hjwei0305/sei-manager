package com.changhong.sei.datamodel.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 标签库(LabelLibrary)DTO类
 *
 * @author sei
 * @since 2020-07-28 23:24:04
 */
@ApiModel(description = "标签库DTO")
public class LabelLibraryDto extends BaseEntityDto {
    private static final long serialVersionUID = 645521401900555116L;
    /**
     * 标识符
     */
    @ApiModelProperty(value = "标识符")
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 类型code
     */
    @ApiModelProperty(value = "类型code")
    private String labelTypeCode;
    /**
     * 类型name
     */
    @ApiModelProperty(value = "类型name")
    private String labelTypeName;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 冻结
     */
    @ApiModelProperty(value = "冻结")
    private Boolean frozen;


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

    public String getLabelTypeCode() {
        return labelTypeCode;
    }

    public void setLabelTypeCode(String labelTypeCode) {
        this.labelTypeCode = labelTypeCode;
    }

    public String getLabelTypeName() {
        return labelTypeName;
    }

    public void setLabelTypeName(String labelTypeName) {
        this.labelTypeName = labelTypeName;
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