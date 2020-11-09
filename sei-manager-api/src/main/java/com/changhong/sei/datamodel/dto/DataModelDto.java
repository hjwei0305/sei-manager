package com.changhong.sei.datamodel.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 数据模型(DataModel)DTO类
 *
 * @author sei
 * @since 2020-07-28 23:23:49
 */
@ApiModel(description = "数据模型DTO")
public class DataModelDto extends BaseEntityDto {
    private static final long serialVersionUID = -69867743981533719L;
    /**
     * 数据源Id
     */
    @ApiModelProperty(value = "数据源Id")
    private String dsId;
    /**
     * 数据源名称
     */
    @ApiModelProperty(value = "数据源名称")
    private String dsName;
    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String tableName;
    /**
     * 模型分类代码
     */
    @ApiModelProperty(value = "模型分类代码")
    private String modelTypeCode;
    /**
     * 模型分类名称
     */
    @ApiModelProperty(value = "模型分类名称")
    private String modelTypeName;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer version = 0;
    /**
     * 是否有修改未发布的模型字段
     */
    @ApiModelProperty(value = "没有修改未发布的模型字段")
    private Boolean published = Boolean.FALSE;
    /**
     * 模型字段清单
     */
    @ApiModelProperty(value = "模型字段清单")
    private List<DataModelFieldDto> fields;

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getModelTypeCode() {
        return modelTypeCode;
    }

    public void setModelTypeCode(String modelTypeCode) {
        this.modelTypeCode = modelTypeCode;
    }

    public String getModelTypeName() {
        return modelTypeName;
    }

    public void setModelTypeName(String modelTypeName) {
        this.modelTypeName = modelTypeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public List<DataModelFieldDto> getFields() {
        return fields;
    }

    public void setFields(List<DataModelFieldDto> fields) {
        this.fields = fields;
    }
}