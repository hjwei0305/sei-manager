package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能：项目tag
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-30 13:53
 */
@ApiModel(description = "项目tag")
public class TagDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -4455924348686616256L;

    /**
     * 应用模块代码
     */
    @ApiModelProperty(notes = "应用模块代码")
    private String moduleCode;
    @ApiModelProperty(notes = "tag名")
    private String name;
    /**
     * 主版本
     */
    @ApiModelProperty(notes = "主版本")
    private Integer major = 0;
    /**
     * 次版本
     */
    @ApiModelProperty(notes = "次版本")
    private Integer minor = 0;
    /**
     * 修订版本
     */
    @ApiModelProperty(notes = "修订版本")
    private Integer revised = 0;
    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    private Long createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(notes = "创建人")
    private String createAccount;
    @ApiModelProperty(notes = "描述")
    private String message;
    @ApiModelProperty(notes = "commitId")
    private String commitId;
    @ApiModelProperty(notes = "是否发布版本")
    private Boolean release = Boolean.FALSE;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Integer getRevised() {
        return revised;
    }

    public void setRevised(Integer revised) {
        this.revised = revised;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRelease() {
        return release;
    }

    public void setRelease(Boolean release) {
        this.release = release;
    }
}
