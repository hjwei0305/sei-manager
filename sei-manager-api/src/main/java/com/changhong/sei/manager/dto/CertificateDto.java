package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 凭证(Certificate)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:33:56
 */
@ApiModel(description = "凭证DTO")
public class CertificateDto extends BaseEntityDto {
    private static final long serialVersionUID = -34967855372921115L;
    /**
     * 凭证名称
     */
    @ApiModelProperty(value = "凭证名称")
    private String name;
    /**
     * 凭证值
     */
    @ApiModelProperty(value = "凭证值")
    private String value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}