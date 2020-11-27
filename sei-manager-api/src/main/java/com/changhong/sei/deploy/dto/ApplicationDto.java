package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-30 13:53
 */
@ApiModel(description = "应用")
public class ApplicationDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -4455924348686616256L;

    @ApiModelProperty(notes = "应用代码")
    private String code;
    @ApiModelProperty(notes = "应用名称")
    private String name;
    @ApiModelProperty(notes = "应用版本")
    private String version;
    @ApiModelProperty(notes = "描述说明")
    private String remark;
    /**
     * 所属组代码
     */
    @ApiModelProperty(notes = "所属组代码")
    private String groupCode;
    /**
     * 所属组名称
     */
    @ApiModelProperty(notes = "所属组名称")
    private String groupName;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

}
