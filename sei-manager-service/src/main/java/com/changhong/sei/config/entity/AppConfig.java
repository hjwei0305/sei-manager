package com.changhong.sei.config.entity;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 应用参数配置(AppConfig)实体类
 *
 * @author sei
 * @since 2021-02-22 21:43:43
 */
@Entity
@Table(name = "conf_app_config")
@DynamicInsert
@DynamicUpdate
public class AppConfig extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 168255479322893154L;
    public static final String FIELD_KEY = "key";
    public static final String FIELD_APP_CODE = "appCode";
    public static final String FIELD_ENV_CODE = "envCode";
    /**
     * 应用服务代码
     */
    @Column(name = "app_code")
    private String appCode;
    /**
     * 应用服务名称
     */
    @Column(name = "app_name")
    private String appName;
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


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

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