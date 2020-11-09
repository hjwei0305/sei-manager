package com.changhong.sei.datamodel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能：数据库类型dto
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-07-30 09:25
 */
@ApiModel(description = "数据库类型DTO")
public class DBTypeDto implements Serializable {
    private static final long serialVersionUID = 8398546378386822283L;

    /**
     * 数据库类型名称
     */
    @ApiModelProperty(value = "数据库类型名称")
    private String name;
    /**
     * 数据库默认端口
     */
    @ApiModelProperty(value = "数据库默认端口")
    private int port;
    /**
     * 数据库连接url模版
     */
    @ApiModelProperty(value = "数据库连接url模版")
    private String temp;

    public DBTypeDto() {
    }

    public DBTypeDto(DBType dbType) {
        this.name = dbType.name();
        this.port = dbType.getPort();
        this.temp = dbType.getUrlTemp();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
