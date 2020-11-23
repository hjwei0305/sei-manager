package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 部署模板(DeployTemplate)实体类
 *
 * @author sei
 * @since 2020-11-23 08:34:03
 */
@Entity
@Table(name = "deploy_template")
@DynamicInsert
@DynamicUpdate
public class DeployTemplate extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 520497168789427262L;
    /**
     * 模板名称
     */
    @Column(name = "name")
    private String name;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}