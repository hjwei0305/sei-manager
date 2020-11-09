package com.changhong.sei.datamodel.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 元素标签关系(ElementLabel)实体类
 *
 * @author sei
 * @since 2020-07-28 17:31:46
 */
@Entity
@Table(name = "element_label")
@DynamicInsert
@DynamicUpdate
public class ElementLabel extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = -44651151076838370L;
    /**
     * 元素id
     */
    @Column(name = "element_id")
    private String elementId;
    /**
     * 标签id
     */
    @Column(name = "label_id")
    private String labelId;


    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

}