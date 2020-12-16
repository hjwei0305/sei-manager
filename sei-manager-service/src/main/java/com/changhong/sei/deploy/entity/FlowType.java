package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.entity.IFrozen;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能：流程类型
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-28 23:04
 */
@Entity
@Table(name = "flow_de_type")
@DynamicInsert
@DynamicUpdate
public class FlowType extends BaseAuditableEntity implements IFrozen, ICodeUnique, Serializable {
    private static final long serialVersionUID = -64497955636689211L;

    /**
     * 代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 模块名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 描述说明
     */
    @Column(name = "version_")
    private Integer version = 0;
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

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    @Override
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }
}
