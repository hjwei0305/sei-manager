package com.changhong.sei.cicd.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.entity.IFrozen;
import com.changhong.sei.cicd.dto.BuildStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 版本发布记录(VersionRecord)实体类
 *
 * @author sei
 * @since 2020-11-26 14:45:20
 */
@Entity
@Table(name = "cicd_version_record")
@DynamicInsert
@DynamicUpdate
public class VersionRecord extends BaseEntity implements IFrozen, Serializable {
    private static final long serialVersionUID = -78400014111481829L;
    public static final String FIELD_APP_ID = "appId";
    public static final String FIELD_MODULE_CODE = "moduleCode";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_AVAILABLE = "available";
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
     * 引用tag
     */
    @Column(name = "ref_tag")
    private String refTag;
    /**
     * 版本名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 版本号
     */
    @Column(name = "version_")
    private String version;
    /**
     * commit id
     */
    @Column(name = "commit_id")
    private String commitId;
    /**
     * 镜像名
     */
    @Column(name = "image_name")
    private String imageName;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 版本创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;
    /**
     * 版本创建时间
     */
    @Column(name = "create_user")
    private String createUser;
    /**
     * 是否冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.TRUE;
    /**
     * 是否是可用的版本
     */
    @Column(name = "available")
    private Boolean available = Boolean.FALSE;
    /**
     * Jenkins构建状态
     */
    @Column(name = "build_status")
    @Enumerated(EnumType.STRING)
    private BuildStatus buildStatus = BuildStatus.NOT_BUILT;

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

    public String getRefTag() {
        return refTag;
    }

    public void setRefTag(String refTag) {
        this.refTag = refTag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    @Override
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }
}