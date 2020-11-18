package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.entity.UserGroup;
import com.changhong.sei.manager.entity.UserGroupUser;
import org.springframework.stereotype.Repository;

/**
 * 用户组用户关系表(UserGroup)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:35
 */
@Repository
public interface UserGroupUserDao extends BaseRelationDao<UserGroupUser, UserGroup, User> {

}