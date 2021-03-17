package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 凭证(Certificate)实体类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Entity
@Table(name = "cicd_certificate")
@DynamicInsert
@DynamicUpdate
public class Certificate extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 556644951408263122L;
    /**
     * 凭证名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 凭证值
     */
    @Column(name = "value")
    private String value;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}