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
 * 用户组用户关系表(UserGroupUser)实体类
 *
 * @author sei
 * @since 2020-11-10 16:24:34
 */
@Entity
@Table(name = "sec_user_group_user")
@DynamicInsert
@DynamicUpdate
public class UserGroupUser extends BaseEntity implements RelationEntity<UserGroup, User>, Serializable {
    private static final long serialVersionUID = 667040883030050793L;

    /**
     * 用户组
     */
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private UserGroup parent;
    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User child;

    @Override
    public UserGroup getParent() {
        return parent;
    }

    @Override
    public void setParent(UserGroup parent) {
        this.parent = parent;
    }

    @Override
    public User getChild() {
        return child;
    }

    @Override
    public void setChild(User child) {
        this.child = child;
    }
}