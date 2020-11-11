package com.changhong.sei.manager.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限表(SecPermission)实体类
 *
 * @author sei
 * @since 2020-11-10 16:24:28
 */
@Entity
@Table(name = "sec_permission")
@DynamicInsert
@DynamicUpdate
public class Permission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -51296555709366977L;
    /**
     * 权限名
     */
    @Column(name = "name")
    private String name;
    /**
     * 类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址
     */
    @Column(name = "url")
    private String url;
    /**
     * 权限类型，页面-1，按钮-2
     */
    @Column(name = "type")
    private Integer type;
    /**
     * 权限表达式
     */
    @Column(name = "permission")
    private String permission;
    /**
     * 后端接口访问方式
     */
    @Column(name = "method")
    private String method;
    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;
    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private String parentId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}