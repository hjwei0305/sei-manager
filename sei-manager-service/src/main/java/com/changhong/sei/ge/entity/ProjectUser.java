package com.changhong.sei.ge.entity;

import com.changhong.sei.common.ObjectType;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 项目用户(ProjectUser)实体类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Entity
@Table(name = "ge_project_user")
@DynamicInsert
@DynamicUpdate
public class ProjectUser extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 954549891503436485L;
    public static final String FIELD_OBJECT_ID = "objectId";
    public static final String FIELD_ACCOUNT = "account";
    public static final String FIELD_TYPE = "type";

    /**
     * 用户账号
     */
    @Column(name = "account")
    private String account;
    /**
     * 用户gitId
     */
    @Column(name = "git_id")
    private Integer gitId;
    /**
     * 对象id
     */
    @Column(name = "object_id")
    private String objectId;
    /**
     * 对象名称
     */
    @Column(name = "object_name")
    private String objectName;
    /**
     * 对象类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "object_type")
    private ObjectType type;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getGitId() {
        return gitId;
    }

    public void setGitId(Integer gitId) {
        this.gitId = gitId;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }
}