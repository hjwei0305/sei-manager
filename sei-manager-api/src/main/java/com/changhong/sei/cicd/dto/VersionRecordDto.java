package com.changhong.sei.cicd.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 版本发布记录(VersionRecord)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@ApiModel(description = "版本发布记录DTO")
public class VersionRecordDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 630890453379821715L;
    /**
     * 所属应用id
     */
    @ApiModelProperty(value = "所属应用id")
    private String appId;
    /**
     * 所属应用id
     */
    @ApiModelProperty(value = "所属应用")
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
     * 引用tag
     */
    @ApiModelProperty(value = "引用tag")
    private String refTag;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;
    /**
     * commit id
     */
    @ApiModelProperty(value = "commit id")
    private String commitId;
    /**
     * 镜像名
     */
    @ApiModelProperty(value = "镜像名")
    private String imageName;
    /**
     * 版本主题
     */
    @ApiModelProperty(value = "版本主题")
    private String name;
    /**
     * 版本创建人
     */
    @ApiModelProperty(value = "版本创建人")
    private String createUser;
    /**
     * 是否冻结
     */
    @ApiModelProperty(value = "是否冻结")
    private Boolean frozen;
    /**
     * 是否是可用的
     */
    @ApiModelProperty(value = "是否是可用的")
    private Boolean available;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 版本创建时间
     */
    @ApiModelProperty(value = "版本创建时间", example = "2020-01-14 22:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

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

    public String getRefTag() {
        return refTag;
    }

    public void setRefTag(String refTag) {
        this.refTag = refTag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}