package com.changhong.sei.config.entity;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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
    public static final String FIELD_KEY = "key";
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
     * 使用状态：NONE、ENABLE、DISABLE
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "use_status")
    private UseStatus useStatus = UseStatus.NONE;
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

    public UseStatus getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(UseStatus useStatus) {
        this.useStatus = useStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        GeneralConfig that = (GeneralConfig) o;

        if (!Objects.equals(envCode, that.envCode)) {
            return false;
        }
        return key != null ? key.equals(that.key) : that.key == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (envCode != null ? envCode.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}