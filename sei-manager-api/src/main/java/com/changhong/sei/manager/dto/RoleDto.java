package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 功能角色DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 21:54
 */
@ApiModel(description = "功能角色DTO")
public class RoleDto extends BaseEntityDto {

    /**
     * 代码
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "代码(max = 50)", required = true)
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "名称(max = 50)", required = true)
    private String name;
    /**
     * 功能角色组Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "功能角色组Id(max = 36)", required = true)
    private String featureRoleGroupId;
    /**
     * 功能角色组代码
     */
    @ApiModelProperty(value = "功能角色组代码")
    private String featureRoleGroupCode;
    /**
     * 功能角色组名称
     */
    @ApiModelProperty(value = "功能角色组名称")
    private String featureRoleGroupName;
    /**
     * 授权分配关系Id
     */
    @ApiModelProperty("授权分配关系Id")
    private String relationId;

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

    public String getFeatureRoleGroupId() {
        return featureRoleGroupId;
    }

    public void setFeatureRoleGroupId(String featureRoleGroupId) {
        this.featureRoleGroupId = featureRoleGroupId;
    }

    public String getFeatureRoleGroupCode() {
        return featureRoleGroupCode;
    }

    public void setFeatureRoleGroupCode(String featureRoleGroupCode) {
        this.featureRoleGroupCode = featureRoleGroupCode;
    }

    public String getFeatureRoleGroupName() {
        return featureRoleGroupName;
    }

    public void setFeatureRoleGroupName(String featureRoleGroupName) {
        this.featureRoleGroupName = featureRoleGroupName;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
}
