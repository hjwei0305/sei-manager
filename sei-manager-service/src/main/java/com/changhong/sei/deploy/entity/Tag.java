package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 * 应用标签(Tag)实体类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Entity
@Table(name = "app_module_tag")
@DynamicInsert
@DynamicUpdate
public class Tag extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 954549891503436485L;
    public static final String FIELD_MODULE_ID = "moduleId";
    public static final String FIELD_MAJOR = "major";
    public static final String FIELD_MINOR = "minor";
    public static final String FIELD_REVISED = "revised";
    public static final String FIELD_CODE = "code";
    /**
     * 应用模块代码
     */
    @Column(name = "module_id")
    private String moduleId;
    /**
     * 应用模块代码
     */
    @Column(name = "module_code")
    private String moduleCode;
    /**
     * 标签名
     */
    @Column(name = "tag_name")
    private String code;
    /**
     * 主版本
     */
    @Column(name = "major")
    private Integer major = 0;
    /**
     * 次版本
     */
    @Column(name = "minor")
    private Integer minor = 0;
    /**
     * 修订版本
     */
    @Column(name = "revised")
    private Integer revised = 0;
    /**
     * 分支
     */
    @Column(name = "branch")
    private String branch;
    /**
     * 是否发布
     */
    @Column(name = "is_release")
    private Boolean release = Boolean.FALSE;
    /**
     * commitId
     */
    @Column(name = "commit_id")
    private String commitId;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String message;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long createTime;
    /**
     * 创建人
     */
    @Column(name = "create_account")
    private String createAccount;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Integer getRevised() {
        return revised;
    }

    public void setRevised(Integer revised) {
        this.revised = revised;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Boolean getRelease() {
        return release;
    }

    public void setRelease(Boolean release) {
        this.release = release;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    @Transient
    public String getTagName() {
        return new StringJoiner(".", "", "")
                .add(String.valueOf(major))
                .add(String.valueOf(minor))
                .add(String.valueOf(revised)).toString();
    }

}