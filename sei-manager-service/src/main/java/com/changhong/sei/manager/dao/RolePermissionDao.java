package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.manager.entity.RolePermission;
import org.springframework.stereotype.Repository;

/**
 * 角色权限关系表(SecRolePermission)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:32
 */
@Repository
public interface RolePermissionDao extends BaseEntityDao<RolePermission> {

}