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
@Table(name = "conf_env_variable")
@DynamicInsert
@DynamicUpdate
public class EnvVariable extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 938743634139683037L;
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
     * 使用状态：NONE、ENABLE、DISABLE
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "use_status")
    private UseStatus useStatus;
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

}