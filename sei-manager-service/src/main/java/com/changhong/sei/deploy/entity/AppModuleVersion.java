package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
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
@Table(name = "app_module_version")
@DynamicInsert
@DynamicUpdate
public class AppModuleVersion extends BaseAuditableEntity implements IFrozen, Serializable {
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
     * 模块git id
     */
    @Column(name = "git_id")
    private String gitId;
    /**
     * 模块名称
     */
    @Column(name = "module_name")
    private String moduleName;
    /**
     * 模块分支
     */
    @Column(name = "branch")
    private String branch;
    /**
     * 版本号
     */
    @Column(name = "version_")
    private String version;
    /**
     * 标签名称
     */
    @Column(name = "tag_name")
    private String tagName;
    @Column(name = "target")
    private String target;
    @Column(name = "commit_id")
    private String commitId;
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

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
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