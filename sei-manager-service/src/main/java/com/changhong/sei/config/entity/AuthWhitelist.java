package com.changhong.sei.config.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实现功能：网关认证白名单
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-15 14:32
 */
@Entity
@Table(name = "conf_auth_whitelist")
@DynamicInsert
@DynamicUpdate
public class AuthWhitelist extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = -5721511820996126312L;
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
     * http方法
     */
    @Column(name = "method")
    private String method;
    /**
     * uri地址
     */
    @Column(name = "uri")
    private String uri;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
