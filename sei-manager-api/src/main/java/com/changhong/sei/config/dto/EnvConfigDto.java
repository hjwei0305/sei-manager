package com.changhong.sei.config.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-05 15:00
 */
@ApiModel(description = "同步配置到其他环境")
public class EnvConfigDto implements Serializable {
    private static final long serialVersionUID = 5319130224008373953L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnvConfigDto that = (EnvConfigDto) o;

        if (!Objects.equals(envCode, that.envCode)) {
            return false;
        }
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        int result = envCode != null ? envCode.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}
