package com.changhong.sei.datamodel.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据源(DataSource)DTO类
 *
 * @author sei
 * @since 2020-07-28 23:24:02
 */
@ApiModel(description = "数据源DTO")
public class DataSourceDto extends BaseEntityDto {
    private static final long serialVersionUID = -41971954355939764L;
    /**
     * 数据库名
     */
    @ApiModelProperty(value = "数据库名")
    private String code;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 数据库类型
     */
    @ApiModelProperty(value = "数据库类型")
    private DBType dbType;
    /**
     * 主机地址
     */
    @ApiModelProperty(value = "主机地址")
    private String host = "127.0.0.1";
    /**
     * 端口
     */
    @ApiModelProperty(value = "端口")
    private Integer port;
    /**
     * url
     */
    @ApiModelProperty(value = "url")
    private String url;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 冻结
     */
    @ApiModelProperty(value = "冻结")
    private Boolean frozen;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DBType getDbType() {
        return dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

}