package com.changhong.sei.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-30 13:53
 */
@ApiModel(description = "应用")
public class ApplicationResponse extends ApplicationDto implements Serializable {
    private static final long serialVersionUID = -4455924348686616256L;

    @ApiModelProperty(notes = "其他属性")
    private Map<String, String> metadata;

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void putMetadata(String key, String value) {
        if (Objects.isNull(metadata)) {
            metadata = new HashMap<>();
        }
        metadata.put(key, value);
    }
}
