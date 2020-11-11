package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.entity.UserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关系表(SecUserRole)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:35
 */
@Repository
public interface UserRoleDao extends BaseRelationDao<UserRole, User, Role> {

}