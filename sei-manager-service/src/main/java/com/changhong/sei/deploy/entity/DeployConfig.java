package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 部署配置(DeployConfig)实体类
 *
 * @author sei
 * @since 2020-11-26 14:45:20
 */
@Entity
@Table(name = "deploy_config")
@DynamicInsert
@DynamicUpdate
public class DeployConfig extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = -78400014111481829L;
    public static final String FIELD_APP_ID = "appId";
    public static final String FIELD_TEMP_ID = "tempId";
    /**
     * 环境
     */
    @Column(name = "env_code")
    private String envCode;
    /**
     * 环境
     */
    @Column(name = "env_name")
    private String envName;

    /**
     * 所属应用id
     */
    @Column(name = "app_id")
    private String appId;
    /**
     * 所属应用id
     */
    @Column(name = "app_name")
    private String appName;
    /**
     * 模块id
     */
    @Column(name = "module_id")
    private String moduleId;
    /**
     * 模块代码
     */
    @Column(name = "module_code")
    private String moduleCode;
    /**
     * 模块名称
     */
    @Column(name = "module_name")
    private String moduleName;
    /**
     * 模版id
     */
    @Column(name = "temp_id")
    private String tempId;
    /**
     * 模版名称
     */
    @Column(name = "temp_name")
    private String tempName;
    /**
     * 节点id(多个逗号分隔)
     */
    @Column(name = "nodes")
    private String nodes;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 环境代码+模块代码
     *
     * @return Jenkins任务名
     */
    @Transient
    public String getJobName() {
        return envCode + "_" + moduleCode;
    }
}