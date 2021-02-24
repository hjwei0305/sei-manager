package com.changhong.sei.config.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 已发布的应用配置(ConfReleasedConfig)DTO类
 *
 * @author sei
 * @since 2021-02-22 21:44:20
 */
@ApiModel(description = "已发布的应用配置DTO")
public class ReleasedConfigDto extends BaseEntityDto {
    private static final long serialVersionUID = -63724786124590371L;
    /**
     * 应用服务代码
     */
    @ApiModelProperty(value = "应用服务代码")
    private String appCode;
    /**
     * 环境代码
     */
    @ApiModelProperty(value = "环境代码")
    private String envCode;
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


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
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

}