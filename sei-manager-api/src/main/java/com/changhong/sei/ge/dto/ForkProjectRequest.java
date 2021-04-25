package com.changhong.sei.ge.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-04-06 11:09
 */
@ApiModel(description = "派生项目")
public class ForkProjectRequest implements Serializable {
    private static final long serialVersionUID = 5809189058607696955L;

    @NotBlank
    @ApiModelProperty(notes = "应用id")
    private String appId;
    @NotBlank
    @ApiModelProperty(notes = "模块id")
    private String moduleId;
    @ApiModelProperty(notes = "派生项目命名空间")
    private String namespace;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
