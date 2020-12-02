package com.changhong.sei.deploy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能：Gitlab项目tag
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-30 13:53
 */
@ApiModel(description = "Gitlab项目tag")
public class GitlabTagDto implements Serializable {
    private static final long serialVersionUID = -4455924348686616256L;

    @ApiModelProperty(notes = "tag名")
    private String name;
    @ApiModelProperty(notes = "描述")
    private String message;
    @ApiModelProperty(notes = "是否发布版本")
    private Boolean release = Boolean.FALSE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
