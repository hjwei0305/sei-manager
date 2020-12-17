package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.deploy.dto.ApplyType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能：流程实例
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 19:04
 */
@Entity
@Table(name = "flow_instance")
@DynamicInsert
@DynamicUpdate
public class FlowInstance extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8488019354515570494L;

    public static final String FIELD_CODE = "code";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_RELATION = "relation";
    // relation字段的默认值
    public static final String RELATION_EMPTY = "-";

    /**
     * 流程类型代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 流程类型名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 流程类型版本
     */
    @Column(name = "version_")
    private Integer version = 0;
    /**
     * 关联值
     */
    @Column(name = "relation")
    private String relation = RELATION_EMPTY;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
