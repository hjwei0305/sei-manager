package com.changhong.sei.manager.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户角色关系表(SecUserRole)实体类
 *
 * @author sei
 * @since 2020-11-10 16:24:34
 */
@Entity
@Table(name = "sec_user_role")
@DynamicInsert
@DynamicUpdate
public class UserRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 667040883030050793L;


}