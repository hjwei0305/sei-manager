package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 实现功能: 功能项的树形节点
 *
 * @author 王锦光 wangjg
 * @version 2020-03-10 14:33
 */
@ApiModel("功能项的树形节点")
public class FeatureNode extends BaseEntityDto {
    private static final long serialVersionUID = -7937331773453414336L;

    /**
     * 功能项名称
     */
    @ApiModelProperty("功能项名称")
    private String name;

    /**
     * 功能项类型
     */
    @ApiModelProperty("功能项类型")
    private Integer type;

    /**
     * 子节点清单
     */
    @ApiModelProperty("子节点清单")
    private List<FeatureNode> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<FeatureNode> getChildren() {
        return children;
    }

    public void setChildren(List<FeatureNode> children) {
        this.children = children;
    }
}
