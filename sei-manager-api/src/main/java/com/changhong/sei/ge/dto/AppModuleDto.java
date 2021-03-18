package com.changhong.sei.ge.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * 应用模块(AppModule)DTO类
 *
 * @author sei
 * @since 2020-11-26 14:45:23
 */
@ApiModel(description = "应用模块DTO")
public class AppModuleDto extends BaseEntityDto {
    private static final long serialVersionUID = -38024625578859016L;
    /**
     * 所属应用id
     */
    @ApiModelProperty(value = "所属应用id")
    private String appId;
    /**
     * 所属应用名称
     */
    @ApiModelProperty(value = "所属应用名称")
    private String appName;
    /**
     * 模块代码
     */
    @ApiModelProperty(value = "模块代码")
    private String code;
    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    private String name;
    /**
     * 应用分组代码
     */
    @ApiModelProperty(value = "应用分组代码")
    private String groupCode;
    /**
     * 应用分组名称
     */
    @ApiModelProperty(value = "应用分组名称")
    private String groupName;
    /**
     * 模块版本
     */
    @ApiModelProperty(value = "模块版本")
    private String version;
    /**
     * 命名空间(包路径)
     */
    @ApiModelProperty(value = "命名空间(包路径)")
    private String nameSpace;
    /**
     * git地址
     */
    @ApiModelProperty(value = "gitId")
    private String gitId;

    /**
     * git地址
     */
    @ApiModelProperty(value = "git HTTP 地址")
    private String gitHttpUrl;
    /**
     * git地址
     */
    @ApiModelProperty(value = "git SSH 地址")
    private String gitSshUrl;
    /**
     * git项目地址
     */
    @ApiModelProperty(value = "git项目地址")
    private String gitWebUrl;
    /**
     * git项目创建时间
     */
    @ApiModelProperty(value = "git项目创建时间", example = "2020-01-14 22:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gitCreateTime;
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

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public String getGitHttpUrl() {
        return gitHttpUrl;
    }

    public void setGitHttpUrl(String gitHttpUrl) {
        this.gitHttpUrl = gitHttpUrl;
    }

    public String getGitSshUrl() {
        return gitSshUrl;
    }

    public void setGitSshUrl(String gitSshUrl) {
        this.gitSshUrl = gitSshUrl;
    }

    public String getGitWebUrl() {
        return gitWebUrl;
    }

    public void setGitWebUrl(String gitWebUrl) {
        this.gitWebUrl = gitWebUrl;
    }

    public LocalDateTime getGitCreateTime() {
        return gitCreateTime;
    }

    public void setGitCreateTime(LocalDateTime gitCreateTime) {
        this.gitCreateTime = gitCreateTime;
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