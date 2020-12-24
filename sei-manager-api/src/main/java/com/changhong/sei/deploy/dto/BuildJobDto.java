package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 构建任务(BuildJob)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@ApiModel(description = "构建任务DTO")
public class BuildJobDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 630890453379821715L;
    /**
     * 环境
     */
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
    @ApiModelProperty(value = "应用id", required = true)
    private String appId;
    /**
     * 所属应用id
     */
    @ApiModelProperty(value = "应用名称")
    private String appName;
    /**
     * 模块git id
     */
    @ApiModelProperty(value = "模块git id", required = true)
    private String gitId;
    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块代码", required = true)
    private String moduleCode;
    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    private String moduleName;
    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称", required = true)
    private String tagName;
    /**
     * 是否冻结
     */
    @ApiModelProperty(value = "是否可用")
    private Boolean frozen;
    /**
     * 发布名称
     */
    @ApiModelProperty(value = "发布名称", required = true)
    private String name;
    /**
     * 描述说明(部署要求,脚本内容等)
     */
    @ApiModelProperty(value = "描述说明(部署要求等)")
    private String remark;
    /**
     * 脚本内容
     */
    @ApiModelProperty(value = "脚本内容")
    private String script;
    /**
     * 期望完成时间
     */
    @ApiModelProperty(value = "期望完成时间", example = "2020-01-14 22:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expCompleteTime;
    /**
     * Jenkins构建状态
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty(value = "Jenkins构建状态")
    private BuildStatus buildStatus;
    /**
     * Jenkins构建时间
     */
    @ApiModelProperty(value = "构建时间", example = "2020-01-14 22:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime buildTime;
    /**
     * Jenkins构建人账号
     */
    @ApiModelProperty(value = "构建人账号")
    private String buildAccount;
    /**
     * 是否允许构建
     */
    @ApiModelProperty(value = "是否允许构建")
    private Boolean allowBuild;

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

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
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

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public LocalDateTime getExpCompleteTime() {
        return expCompleteTime;
    }

    public void setExpCompleteTime(LocalDateTime expCompleteTime) {
        this.expCompleteTime = expCompleteTime;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }

    public LocalDateTime getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(LocalDateTime buildTime) {
        this.buildTime = buildTime;
    }

    public String getBuildAccount() {
        return buildAccount;
    }

    public void setBuildAccount(String buildAccount) {
        this.buildAccount = buildAccount;
    }

    public Boolean getAllowBuild() {
        return allowBuild;
    }

    public void setAllowBuild(Boolean allowBuild) {
        this.allowBuild = allowBuild;
    }
}