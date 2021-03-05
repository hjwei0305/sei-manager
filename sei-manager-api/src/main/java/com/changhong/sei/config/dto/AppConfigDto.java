package com.changhong.sei.config.dto;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * 应用参数配置(ConfAppConfig)DTO类
 *
 * @author sei
 * @since 2021-02-22 21:43:49
 */
@ApiModel(description = "应用参数配置DTO")
public class AppConfigDto extends BaseEntityDto {
    private static final long serialVersionUID = 949135482532159294L;
    /**
     * 应用服务代码
     */
    @ApiModelProperty(value = "应用服务代码")
    private String appCode;
    /**
     * 应用服务名称
     */
    @ApiModelProperty(value = "应用服务名称")
    private String appName;
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
    private UseStatus useStatus = UseStatus.NONE;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppConfigDto that = (AppConfigDto) o;

        if (!Objects.equals(appCode, that.appCode)) {
            return false;
        }
        if (!Objects.equals(envCode, that.envCode)) {
            return false;
        }
        if (!Objects.equals(key, that.key)) {
            return false;
        }
        return useStatus == that.useStatus;
    }

    @Override
    public int hashCode() {
        int result = appCode != null ? appCode.hashCode() : 0;
        result = 31 * result + (envCode != null ? envCode.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (useStatus != null ? useStatus.hashCode() : 0);
        return result;
    }
}