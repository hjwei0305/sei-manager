package com.changhong.sei.config.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 通用参数配置(ConfGlobalConfig)实体类
 *
 * @author sei
 * @since 2021-02-22 21:44:03
 */
@Entity
@Table(name = "conf_general_config")
@DynamicInsert
@DynamicUpdate
public class GeneralConfig extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = -76565844866333714L;
    public static final String FIELD_ENV_CODE = "envCode";
    /**
     * 环境代码
     */
    @Column(name = "env_code")
    private String envCode;
    /**
     * 环境名称
     */
    @Column(name = "env_name")
    private String envName;
    /**
     * 配置键
     */
    @Column(name = "key")
    private String key;
    /**
     * 配置值
     */
    @Column(name = "value")
    private String value;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;


    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}