package com.changhong.sei.cicd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 应用模块(AppModule)DTO类
 *
 * @author sei
 * @since 2020-11-26 14:45:23
 */
@ApiModel(description = "应用模块申请单DTO")
public class AppModuleRequisitionDto extends RequisitionDto implements Serializable {
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
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}