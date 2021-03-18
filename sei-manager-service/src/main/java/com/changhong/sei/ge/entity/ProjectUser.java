package com.changhong.sei.ge.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 项目用户(ProjectUser)实体类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Entity
@Table(name = "project_user")
@DynamicInsert
@DynamicUpdate
public class ProjectUser extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 954549891503436485L;
    public static final String FIELD_GROUP_CODE = "groupCode";
    public static final String FIELD_APP_ID = "appId";
    public static final String FIELD_MODULE_ID = "moduleId";
    public static final String FIELD_ACCOUNT = "account";
    /**
     * 阶段名称
     */
    @Column(name = "group_code")
    private String groupCode;
    /**
     * 是否冻结
     */
    @Column(name = "group_name")
    private String groupName;
    /**
     * 地址
     */
    @Column(name = "app_id")
    private String appId;
    /**
     * 环境
     */
    @Column(name = "app_name")
    private String appName;
    /**
     * 环境
     */
    @Column(name = "module_id")
    private String moduleId;
    /**
     * 凭证id
     */
    @Column(name = "module_name")
    private String moduleName;
    /**
     * 凭证名称
     */
    @Column(name = "account")
    private String account;
    /**
     * 描述说明
     */
    @Column(name = "user_name")
    private String userName;

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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}