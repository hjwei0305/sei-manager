package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能: 功能角色分配的功能项DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:27
 */
@ApiModel(description = "功能角色分配的功能项DTO")
public class RoleFeatureDto extends BaseEntityDto
        implements RelationEntityDto<RoleDto, FeatureDto>, Serializable {
    private static final long serialVersionUID = 4402539846677796452L;
    /**
     * 功能角色DTO
     */
    @ApiModelProperty(value = "功能角色DTO", required = true)
    private RoleDto parent;

    /**
     * 功能项DTO
     */
    @ApiModelProperty(value = "功能项DTO", required = true)
    private FeatureDto child;

    @Override
    public RoleDto getParent() {
        return parent;
    }

    @Override
    public void setParent(RoleDto parent) {
        this.parent = parent;
    }

    @Override
    public FeatureDto getChild() {
        return child;
    }

    @Override
    public void setChild(FeatureDto child) {
        this.child = child;
    }
}
