package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.entity.IFrozen;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用模块(AppModule)实体类
 *
 * @author sei
 * @since 2020-11-26 14:45:20
 */
@Entity
@Table(name = "app_module")
@DynamicInsert
@DynamicUpdate
public class AppModule extends BaseAuditableEntity implements ICodeUnique, IFrozen, Serializable {
    private static final long serialVersionUID = -78400014111481829L;
    public static final String FIELD_APP_ID = "appId";
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
     * 模块代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 模块名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 模块版本
     */
    @Column(name = "version_")
    private String version;
    /**
     * 命名空间(包路径)
     */
    @Column(name = "name_space")
    private String nameSpace;
    /**
     * gitId
     */
    @Column(name = "git_id")
    private String gitId;
    /**
     * git地址
     */
    @Column(name = "git_http_url")
    private String gitHttpUrl;
    @Column(name = "git_ssh_url")
    private String gitSshUrl;
    @Column(name = "git_web_url")
    private String gitWebUrl;
    @Column(name = "git_create_time")
    private LocalDateTime gitCreateTime;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.TRUE;

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

    @Override
    public String getCode() {
        return code;
    }

    @Override
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

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public String getGitHttpUrl() {
        return gitHttpUrl;
    }

    public void setGitHttpUrl(String gitHttpUrl) {
        this.gitHttpUrl = gitHttpUrl;
    }

    public String getGitSshUrl() {
        return gitSshUrl;
    }

    public void setGitSshUrl(String gitSshUrl) {
        this.gitSshUrl = gitSshUrl;
    }

    public String getGitWebUrl() {
        return gitWebUrl;
    }

    public void setGitWebUrl(String gitWebUrl) {
        this.gitWebUrl = gitWebUrl;
    }

    public LocalDateTime getGitCreateTime() {
        return gitCreateTime;
    }

    public void setGitCreateTime(LocalDateTime gitCreateTime) {
        this.gitCreateTime = gitCreateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    @Override
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }
}