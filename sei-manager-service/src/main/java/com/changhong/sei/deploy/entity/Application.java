package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.IFrozen;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 应用服务(Application)实体类
 *
 * @author sei
 * @since 2020-10-30 15:20:21
 */
@Entity
@Table(name = "application")
@DynamicInsert
@DynamicUpdate
public class Application extends BaseAuditableEntity implements IFrozen, Serializable {
    private static final long serialVersionUID = -64497955636689211L;
    public static final String FIELD_GROUP_CODE = "groupCode";
    /**
     * 应用代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 应用名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 应用版本
     */
    @Column(name = "version_")
    private String version;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 所属组代码
     */
    @Column(name = "group_code")
    private String groupCode;
    /**
     * 所属组代码
     */
    @Column(name = "group_name")
    private String groupName;
    /**
     * 是否冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.TRUE;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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