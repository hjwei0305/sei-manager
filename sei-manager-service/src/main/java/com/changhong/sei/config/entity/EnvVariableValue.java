package com.changhong.sei.config.entity;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 环境变量(ConfEnvVariable)实体类
 *
 * @author sei
 * @since 2021-03-02 14:26:19
 */
@Entity
@Table(name = "conf_env_variable_value")
@DynamicInsert
@DynamicUpdate
public class EnvVariableValue extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 938743634139683037L;
    public static final String FIELD_KEY = "key";
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
    @Column(name = "key_code")
    private String key;
    /**
     * 配置值
     */
    @Column(name = "key_value")
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