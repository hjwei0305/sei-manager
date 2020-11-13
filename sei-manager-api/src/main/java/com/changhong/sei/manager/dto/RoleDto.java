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
    private static final long serialVersionUID = -682194106083102055L;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "名称(max = 50)", required = true)
    private String name;
    @Size(max = 100)
    @ApiModelProperty(value = "描述(max = 100)")
    private String description;

    /**
     * 授权分配关系Id
     */
    @ApiModelProperty("授权分配关系Id")
    private String relationId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
}
