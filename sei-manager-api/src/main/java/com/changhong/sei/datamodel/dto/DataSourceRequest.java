package com.changhong.sei.datamodel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据源(DataSource)DTO类
 *
 * @author sei
 * @since 2020-07-28 23:24:02
 */
@ApiModel(description = "创建数据源DTO")
public class DataSourceRequest extends DataSourceDto {
    private static final long serialVersionUID = -41971954355939764L;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}