package com.changhong.sei.deploy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 实现功能：Gitlab项目tag
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-30 13:53
 */
@ApiModel(description = "创建tag标签请求")
public class CreateTagRequest implements Serializable {
    private static final long serialVersionUID = -4455924348686616256L;

    @NotBlank
    @ApiModelProperty(notes = "gitId")
    private String gitId;
    @NotBlank
    @ApiModelProperty(notes = "tag名")
    private String tagName;
    @ApiModelProperty(notes = "分支名")
    private String branch = "master";
    @NotBlank
    @ApiModelProperty(notes = "描述")
    private String message;

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public String getTagName() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
