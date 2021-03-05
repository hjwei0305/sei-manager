package com.changhong.sei.config.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 已发布的应用配置(ConfReleasedConfig)实体类
 *
 * @author sei
 * @since 2021-02-22 21:44:16
 */
@Entity
@Table(name = "conf_released_config")
@DynamicInsert
@DynamicUpdate
public class ReleasedConfig extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 398877085315740550L;
    /**
     * 应用服务代码
     */
    @Column(name = "app_code")
    private String appCode;
    /**
     * 环境代码
     */
    @Column(name = "env_code")
    private String envCode;
    /**
     * 配置键
     */
    @Column(name = "key_code")
    private String key;
    /**
     * 配置值
     */
    @Column(name = "key_value")
    private String value;


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}