package com.changhong.sei.cicd.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.StringJoiner;

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
     * 应用模块
     */
    @NotBlank
    @ApiModelProperty(notes = "应用模块id", required = true)
    private String moduleId;
    @ApiModelProperty(notes = "应用模块代码")
    private String moduleCode;
    @ApiModelProperty(notes = "tag名")
    private String tagName;

    @ApiModelProperty(notes = "分支")
    private String branch;
    /**
     * 主版本
     */
    @Min(0)
    @Max(99)
    @ApiModelProperty(notes = "主版本", required = true)
    private Integer major = 0;
    /**
     * 次版本
     */
    @Min(0)
    @Max(999)
    @ApiModelProperty(notes = "次版本", required = true)
    private Integer minor = 0;
    /**
     * 修订版本
     */
    @Min(0)
    @Max(9999)
    @ApiModelProperty(notes = "修订版本", required = true)
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

    public String getTagName() {
        if (StringUtils.isBlank(tagName)) {
            tagName = new StringJoiner(".", "", "")
                    .add(String.valueOf(major))
                    .add(String.valueOf(minor))
                    .add(String.valueOf(revised)).toString();
        }
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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
