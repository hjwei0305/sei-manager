package com.changhong.sei.config.dto;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * 通用参数配置(GeneralConfig)DTO类
 *
 * @author sei
 * @since 2021-02-22 21:44:07
 */
@ApiModel(description = "通用参数配置DTO")
public class GeneralConfigDto extends BaseEntityDto {
    private static final long serialVersionUID = 551282924714662198L;
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
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 使用状态：NONE、ENABLE、DISABLE
     */
    @ApiModelProperty(value = "使用状态：NONE、ENABLE、DISABLE")
    private UseStatus useStatus = UseStatus.NONE;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UseStatus getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(UseStatus useStatus) {
        this.useStatus = useStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeneralConfigDto that = (GeneralConfigDto) o;

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
        int result = envCode != null ? envCode.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (useStatus != null ? useStatus.hashCode() : 0);
        return result;
    }
}