package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 实现功能: 功能项DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 20:51
 */
@ApiModel(description = "功能项DTO")
public class FeatureDto extends BaseEntityDto {
    private static final long serialVersionUID = -5851073755279966733L;
    /**
     * 功能项名称
     */
    @NotBlank
    @Size(max = 30)
    @ApiModelProperty(value = "功能项名称(max = 30)", required = true)
    private String name;

    /**
     * 类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址
     */
    @ApiModelProperty(value = "类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址(max = 400)")
    private String url;
    /**
     * 外部URL或扩展URL
     */
    @ApiModelProperty(value = "外部URL或扩展URL")
    private String extUrl;

    /**
     * 权限类型，页面-1，按钮-2
     */
    @ApiModelProperty(value = "权限类型，页面-1，按钮-2", required = true)
    @NotNull
    private Integer type;
    /**
     * 权限表达式
     */
    @ApiModelProperty(value = "权限表达式")
    private String permission;
    /**
     * 后端接口访问方式
     */
    @ApiModelProperty(value = "后端接口访问方式")
    private String method;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer rank = 0;
    /**
     * 功能项组Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "功能项组Id(max = 36)")
    private String parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtUrl() {
        return extUrl;
    }

    public void setExtUrl(String extUrl) {
        this.extUrl = extUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
