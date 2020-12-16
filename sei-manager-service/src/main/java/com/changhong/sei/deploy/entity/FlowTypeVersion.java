package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能：流程类型版本记录
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-28 23:04
 */
@Entity
@Table(name = "flow_de_type_version")
public class FlowTypeVersion extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = -64497955636689211L;
    public static final String FIELD_TYPE_ID = "typeId";
    public static final String FIELD_VERSION = "version";
    /**
     * 流程类型
     */
    @Column(name = "type_id", nullable = false)
    private String typeId;
    /**
     * 流程类型版本
     */
    @Column(name = "version_")
    private Integer version = 0;
    /**
     * 流程类型名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 发布时间
     */
    @Column(name = "published_time")
    private LocalDateTime publishedTime;
    /**
     * 发布人账号
     */
    @Column(name = "published_account")
    private String publishedAccount;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(LocalDateTime publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getPublishedAccount() {
        return publishedAccount;
    }

    public void setPublishedAccount(String publishedAccount) {
        this.publishedAccount = publishedAccount;
    }
}
