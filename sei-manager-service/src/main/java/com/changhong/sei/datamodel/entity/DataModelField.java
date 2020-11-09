package com.changhong.sei.datamodel.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 模型字段(DataModelField)实体类
 *
 * @author sei
 * @since 2020-07-28 17:31:42
 */
@Entity
@Table(name = "data_model_field")
@DynamicInsert
@DynamicUpdate
public class DataModelField extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 635902029091908186L;

    public static final String FIELD_DATA_MODEL_ID = "dataModelId";
    public static final String FIELD_FIELD_NAME = "fieldName";

    /**
     * 数据模型id
     */
    @Column(name = "data_model_id")
    private String dataModelId;
    /**
     * 字段名
     */
    @Column(name = "field_name")
    private String fieldName;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 类型
     */
    @Column(name = "data_type")
    private String dataType;
    /**
     * 类型描述
     */
    @Column(name = "data_type_desc")
    private String dataTypeDesc;
    /**
     * 长度
     */
    @Column(name = "data_length")
    private Integer dataLength;
    /**
     * 精度
     */
    @Column(name = "decimal_places")
    private Integer precision;
    /**
     * 是否主键
     */
    @Column(name = "primary_key")
    private Boolean primaryKey = Boolean.FALSE;
    /**
     * 是否为空
     */
    @Column(name = "not_null")
    private Boolean notNull = Boolean.FALSE;
    /**
     * 默认值
     */
    @Column(name = "default_value")
    private String defaultValue;
    /**
     * 外键关联id
     */
    @Column(name = "foreign_key")
    private String foreignKey;
    /**
     * 排序
     */
    @Column(name = "rank")
    private Integer rank = 0;
    /**
     * 是否发布
     */
    @Column(name = "published")
    private Boolean published = Boolean.FALSE;


    public String getDataModelId() {
        return dataModelId;
    }

    public void setDataModelId(String dataModelId) {
        this.dataModelId = dataModelId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeDesc() {
        return dataTypeDesc;
    }

    public void setDataTypeDesc(String dataTypeDesc) {
        this.dataTypeDesc = dataTypeDesc;
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

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

}