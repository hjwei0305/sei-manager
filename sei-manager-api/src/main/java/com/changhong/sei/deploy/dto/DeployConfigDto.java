package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 部署配置(DeployConfig)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@ApiModel(description = "部署配置DTO")
public class DeployConfigDto extends BaseEntityDto {
    private static final long serialVersionUID = 630890453379821715L;

    /**
     * 环境
     */
    @NotBlank
    @ApiModelProperty(value = "环境代码", required = true)
    private String envCode;
    /**
     * 环境
     */
    @ApiModelProperty(value = "环境名称")
    private String envName;

    /**
     * 所属应用id
     */
    @NotBlank
    @ApiModelProperty(value = "所属应用id", required = true)
    private String appId;
    /**
     * 所属应用
     */
    @ApiModelProperty(value = "所属应用")
    private String appName;
    /**
     * 模块id
     */
    @NotBlank
    @ApiModelProperty(value = "模块id", required = true)
    private String moduleId;
    /**
     * 模块代码
     */
    @NotBlank
    @ApiModelProperty(value = "模块代码", required = true)
    private String moduleCode;
    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    private String moduleName;
    /**
     * 模版id
     */
    @ApiModelProperty(value = "模版id", required = true)
    private String tempId;
    /**
     * 模版名称
     */
    @ApiModelProperty(value = "模版名称")
    private String tempName;
    /**
     * 节点id(多个逗号分隔)
     */
    @ApiModelProperty(value = "节点id(多个逗号分隔)")
    private String nodes;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 是否需要发布版本
     */
    @ApiModelProperty(value = "是否需要发布版本", required = true)
    private Boolean needRelease = Boolean.FALSE;

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
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

    public Boolean getNeedRelease() {
        return needRelease;
    }

    public void setNeedRelease(Boolean needRelease) {
        this.needRelease = needRelease;
    }
}