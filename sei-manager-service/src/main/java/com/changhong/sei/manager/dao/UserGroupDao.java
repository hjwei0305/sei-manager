package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.manager.entity.UserGroup;
import org.springframework.stereotype.Repository;

/**
 * 用户组表(UserGroup)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:30
 */
@Repository
public interface UserGroupDao extends BaseEntityDao<UserGroup> {

}