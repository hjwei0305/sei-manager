package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.entity.IFrozen;
import com.changhong.sei.deploy.dto.BuildStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 发布记录(ReleaseRecord)实体类
 *
 * @author sei
 * @since 2020-11-26 14:45:20
 */
@Entity
@Table(name = "release_record")
@DynamicInsert
@DynamicUpdate
public class ReleaseRecord extends BaseEntity implements IFrozen, Serializable {
    private static final long serialVersionUID = -78400014111481829L;
    public static final String FIELD_APP_ID = "appId";
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
     * 标签名称
     */
    @Column(name = "tag_name")
    private String tagName;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 描述说明(部署要求,脚本内容等)
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 是否冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.TRUE;
    /**
     * 期望完成时间
     */
    @Column(name = "exp_complete_time")
    private LocalDateTime expCompleteTime;
    /**
     * Jenkins构建号
     */
    @Column(name = "build_number")
    private Integer buildNumber;
    /**
     * Jenkins构建状态
     */
    @Column(name = "build_status")
    @Enumerated(EnumType.STRING)
    private BuildStatus buildStatus = BuildStatus.NOT_BUILT;

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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    @Override
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public LocalDateTime getExpCompleteTime() {
        return expCompleteTime;
    }

    public void setExpCompleteTime(LocalDateTime expCompleteTime) {
        this.expCompleteTime = expCompleteTime;
    }

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
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