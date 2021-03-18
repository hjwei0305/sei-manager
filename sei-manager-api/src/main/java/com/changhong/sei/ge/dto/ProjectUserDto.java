package com.changhong.sei.ge.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目用户DTO
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@ApiModel(description = "项目用户DTO")
public class ProjectUserDto extends BaseEntityDto {
    private static final long serialVersionUID = 630890453379821715L;
    /**
     * 阶段名称
     */
    @ApiModelProperty(value = "阶段名称")
    private String name;
    /**
     * 是否冻结
     */
    @ApiModelProperty(value = "是否冻结")
    private Boolean frozen = Boolean.FALSE;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 环境
     */
    @ApiModelProperty(value = "环境代码")
    private String envCode;
    /**
     * 环境
     */
    @ApiModelProperty(value = "环境名称")
    private String envName;
    /**
     * 凭证id
     */
    @ApiModelProperty(value = "凭证id")
    private String certificateId;
    /**
     * 凭证名称
     */
    @ApiModelProperty(value = "凭证名称")
    private String certificateName;
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

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}