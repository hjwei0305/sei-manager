package com.changhong.sei.datamodel.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据类型(DataType)DTO类
 *
 * @author sei
 * @since 2020-07-28 23:24:02
 */
@ApiModel(description = "数据类型DTO")
public class DataTypeDto extends BaseEntityDto {
    private static final long serialVersionUID = -85454311939923419L;
    /**
     * 标识符
     */
    @ApiModelProperty(value = "标识符")
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 长度
     */
    @ApiModelProperty(value = "长度")
    private Integer dataLength;
    /**
     * 精度
     */
    @ApiModelProperty(value = "精度")
    private Integer precision;
    /**
     * java类型
     */
    @ApiModelProperty(value = "java类型")
    private String javaType;
    /**
     * mysql类型
     */
    @ApiModelProperty(value = "mysql类型")
    private String mysqlType;
    /**
     * postgre类型
     */
    @ApiModelProperty(value = "postgre类型")
    private String postgreType;
    /**
     * oracle类型
     */
    @ApiModelProperty(value = "oracle类型")
    private String oracleType;
    /**
     * mssql类型
     */
    @ApiModelProperty(value = "mssql类型")
    private String mssqlType;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 是否为系统级数据
     */
    @ApiModelProperty(value = "是否为系统级数据")
    private Boolean system;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getMysqlType() {
        return mysqlType;
    }

    public void setMysqlType(String mysqlType) {
        this.mysqlType = mysqlType;
    }

    public String getPostgreType() {
        return postgreType;
    }

    public void setPostgreType(String postgreType) {
        this.postgreType = postgreType;
    }

    public String getOracleType() {
        return oracleType;
    }

    public void setOracleType(String oracleType) {
        this.oracleType = oracleType;
    }

    public String getMssqlType() {
        return mssqlType;
    }

    public void setMssqlType(String mssqlType) {
        this.mssqlType = mssqlType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

}