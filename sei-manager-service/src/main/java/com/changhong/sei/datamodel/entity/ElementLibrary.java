package com.changhong.sei.datamodel.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 元素库(ElementLibrary)实体类
 *
 * @author sei
 * @since 2020-07-28 17:31:46
 */
@Entity
@Table(name = "element_library")
@DynamicInsert
@DynamicUpdate
public class ElementLibrary extends BaseAuditableEntity implements Serializable, ICodeUnique {
    private static final long serialVersionUID = -34849428874089655L;
    /**
     * 代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
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
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.FALSE;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

}