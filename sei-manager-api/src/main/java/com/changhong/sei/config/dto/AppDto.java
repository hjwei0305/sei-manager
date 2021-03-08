package com.changhong.sei.config.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * 应用模块(AppModule)DTO类
 *
 * @author sei
 * @since 2020-11-26 14:45:23
 */
@ApiModel(description = "应用模块DTO")
public class AppDto extends BaseEntityDto {
    private static final long serialVersionUID = -38024625578859016L;
    /**
     * 模块代码
     */
    @ApiModelProperty(value = "模块代码")
    private String code;
    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}