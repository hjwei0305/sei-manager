package com.changhong.sei.config.dto;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 环境变量(ConfEnvVariable)DTO类
 *
 * @author sei
 * @since 2021-03-02 14:26:32
 */
@ApiModel(description = "环境变量DTO")
public class EnvVariableDto extends BaseEntityDto {
    private static final long serialVersionUID = 249277880977324386L;

    /**
     * 配置键
     */
    @NotBlank
    @ApiModelProperty(value = "配置键")
    private String code;
    /**
     * 使用状态：NONE、ENABLE、DISABLE
     */
    @ApiModelProperty(value = "使用状态：NONE、ENABLE、DISABLE")
    private UseStatus useStatus = UseStatus.NONE;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UseStatus getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(UseStatus useStatus) {
        this.useStatus = useStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}