package com.changhong.sei.datamodel.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 元素标签关系(ElementLabel)DTO类
 *
 * @author sei
 * @since 2020-07-28 23:24:03
 */
@ApiModel(description = "元素标签关系DTO")
public class ElementLabelDto extends BaseEntityDto {
    private static final long serialVersionUID = -18914596817958146L;
    /**
     * 元素id
     */
    @ApiModelProperty(value = "元素id")
    private String elementId;
    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private String labelId;


    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

}