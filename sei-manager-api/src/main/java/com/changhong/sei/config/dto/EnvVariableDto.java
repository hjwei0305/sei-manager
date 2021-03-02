package com.changhong.sei.config.dto;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
     * 环境代码
     */
    @ApiModelProperty(value = "环境代码")
    private String envCode;
    /**
     * 环境名称
     */
    @ApiModelProperty(value = "环境名称")
    private String envName;
    /**
     * 配置键
     */
    @ApiModelProperty(value = "配置键")
    private String key;
    /**
     * 配置值
     */
    @ApiModelProperty(value = "配置值")
    private String value;
    /**
     * 使用状态：NONE、ENABLE、DISABLE
     */
    @ApiModelProperty(value = "使用状态：NONE、ENABLE、DISABLE")
    private UseStatus useStatus;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;


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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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