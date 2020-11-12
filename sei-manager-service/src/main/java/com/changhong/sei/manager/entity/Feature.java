package com.changhong.sei.manager.entity;

import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 功能项权限表(SecFeature)实体类
 *
 * @author sei
 * @since 2020-11-10 16:24:28
 */
@Entity
@Table(name = "sec_feature")
@DynamicInsert
@DynamicUpdate
public class Feature extends BaseEntity implements Serializable, IRank {
    private static final long serialVersionUID = -51296555709366977L;

    public static final String FIELD_PARENT_ID = "parentId";

    /**
     * 功能项名
     */
    @Column(name = "name")
    private String name;
    /**
     * 类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址
     */
    @Column(name = "url")
    private String url;
    /**
     * 外部URL或扩展URL
     */
    @Column(name = "ext_url")
    private String extUrl;
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
    @Column(name = "rank")
    private Integer rank;
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

    public String getExtUrl() {
        return extUrl;
    }

    public void setExtUrl(String extUrl) {
        this.extUrl = extUrl;
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

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}