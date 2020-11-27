package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.IFrozen;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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
public class AppModule extends BaseAuditableEntity implements IFrozen, Serializable {
    private static final long serialVersionUID = -78400014111481829L;
    /**
     * 所属应用id
     */
    @Column(name = "app_id")
    private String appId;
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
    @Column(name = "git_url")
    private String gitUrl;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.FALSE;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCode() {
        return code;
    }

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

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

}