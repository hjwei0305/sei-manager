package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
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
@Table(name = "application_service")
@DynamicInsert
@DynamicUpdate
public class Application extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = -64497955636689211L;
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
     * 应用地址
     */
    @Column(name = "uri")
    private String uri;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}