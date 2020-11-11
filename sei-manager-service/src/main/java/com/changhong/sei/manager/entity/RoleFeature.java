package com.changhong.sei.manager.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.entity.RelationEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色权限关系表(SecRoleFeature)实体类
 *
 * @author sei
 * @since 2020-11-10 16:24:31
 */
@Entity
@Table(name = "sec_role_feature")
@DynamicInsert
@DynamicUpdate
public class RoleFeature extends BaseEntity implements RelationEntity<Role, Feature>, Serializable {
    private static final long serialVersionUID = 968598213653869516L;

    /**
     * 功能角色
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role parent;
    /**
     * 功能项
     */
    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    private Feature child;

    @Override
    public Role getParent() {
        return parent;
    }

    @Override
    public void setParent(Role parent) {
        this.parent = parent;
    }

    @Override
    public Feature getChild() {
        return child;
    }

    @Override
    public void setChild(Feature child) {
        this.child = child;
    }
}