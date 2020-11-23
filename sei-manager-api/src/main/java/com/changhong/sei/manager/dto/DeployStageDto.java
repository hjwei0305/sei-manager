package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部署阶段(DeployStage)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:02
 */
@ApiModel(description = "部署阶段DTO")
public class DeployStageDto extends BaseEntityDto {
    private static final long serialVersionUID = -50461186309148987L;
    /**
     * 阶段名称
     */
    @ApiModelProperty(value = "阶段名称")
    private String name;
    /**
     * 凭证值
     */
    @ApiModelProperty(value = "凭证值")
    private String certificate;
    /**
     * 脚本
     */
    @ApiModelProperty(value = "脚本")
    private String playscript;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getPlayscript() {
        return playscript;
    }

    public void setPlayscript(String playscript) {
        this.playscript = playscript;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}