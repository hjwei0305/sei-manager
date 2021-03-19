package com.changhong.sei.cicd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 构建任务申请单(BuildJobRequisition)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@ApiModel(description = "构建任务申请单DTO")
public class BuildJobRequisitionDto extends RequisitionDto implements Serializable {
    private static final long serialVersionUID = 630890453379821715L;
    /**
     * 环境
     */
    @ApiModelProperty(value = "环境代码")
    private String envCode;
    /**
     * 环境
     */
    @ApiModelProperty(value = "环境名称")
    private String envName;
    /**
     * 所属应用id
     */
    @ApiModelProperty(value = "应用id")
    private String appId;
    /**
     * 所属应用id
     */
    @ApiModelProperty(value = "应用名称")
    private String appName;
    /**
     * 模块git id
     */
    @ApiModelProperty(value = "模块git id")
    private String gitId;
    /**
     * 模块id
     */
    @ApiModelProperty(value = "模块id")
    private String moduleId;
    /**
     * 模块代码
     */
    @ApiModelProperty(value = "模块代码")
    private String moduleCode;
    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    private String moduleName;
    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String tagName;
    /**
     * 发布名称
     */
    @ApiModelProperty(value = "发布名称")
    private String name;
    @ApiModelProperty(value = "构建状态")
    private String buildStatus = BuildStatus.NOT_BUILT.name();
    /**
     * 描述说明(部署要求,脚本内容等)
     */
    @ApiModelProperty(value = "描述说明(部署要求,脚本内容等)")
    private String remark;
    /**
     * 期望完成时间
     */
    @ApiModelProperty(value = "期望完成时间", example = "2020-01-14 22:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expCompleteTime;

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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getExpCompleteTime() {
        return expCompleteTime;
    }

    public void setExpCompleteTime(LocalDateTime expCompleteTime) {
        this.expCompleteTime = expCompleteTime;
    }
}