package com.changhong.sei.manager.entity;

import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色权限关系表(SecRolePermission)实体类
 *
 * @author sei
 * @since 2020-11-10 16:24:31
 */
@Entity
@Table(name = "sec_role_permission")
@DynamicInsert
@DynamicUpdate
public class RolePermission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 968598213653869516L;


}