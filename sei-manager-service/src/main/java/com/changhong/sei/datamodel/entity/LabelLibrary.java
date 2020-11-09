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
 * 标签库(LabelLibrary)实体类
 *
 * @author sei
 * @since 2020-07-28 17:31:47
 */
@Entity
@Table(name = "label_library")
@DynamicInsert
@DynamicUpdate
public class LabelLibrary extends BaseAuditableEntity implements Serializable, ICodeUnique {
    private static final long serialVersionUID = 726975922637160989L;
    /**
     * 标识符
     */
    @Column(name = "code")
    private String code;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 类型code
     */
    @Column(name = "label_type_code")
    private String labelTypeCode;
    /**
     * 类型name
     */
    @Column(name = "label_type_name")
    private String labelTypeName;
    /**
     * 描述说明
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

    public String getLabelTypeCode() {
        return labelTypeCode;
    }

    public void setLabelTypeCode(String labelTypeCode) {
        this.labelTypeCode = labelTypeCode;
    }

    public String getLabelTypeName() {
        return labelTypeName;
    }

    public void setLabelTypeName(String labelTypeName) {
        this.labelTypeName = labelTypeName;
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