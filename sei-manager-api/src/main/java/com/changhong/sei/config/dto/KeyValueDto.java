package com.changhong.sei.config.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-05 15:00
 */
@ApiModel(description = "KeyValue配置dto")
public class KeyValueDto implements Serializable {
    private static final long serialVersionUID = 5319130224008373953L;

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

    public KeyValueDto() {
    }

    public KeyValueDto(String key, String value) {
        this.key = key;
        this.value = value;
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

        KeyValueDto that = (KeyValueDto) o;

        if (!Objects.equals(key, that.key)) {
            return false;
        }
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
