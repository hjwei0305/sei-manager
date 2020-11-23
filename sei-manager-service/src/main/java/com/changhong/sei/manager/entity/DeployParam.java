package com.changhong.sei.manager.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 部署参数(DeployParam)实体类
 *
 * @author sei
 * @since 2020-11-23 08:33:59
 */
@Entity
@Table(name = "deploy_param")
@DynamicInsert
@DynamicUpdate
public class DeployParam extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 914060085873766278L;
    /**
     * 阶段id
     */
    @Column(name = "stage_id")
    private String stageId;
    /**
     * 参数代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 参数名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 值
     */
    @Column(name = "value")
    private String value;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;


    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

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