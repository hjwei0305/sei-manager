package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.manager.entity.Feature;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.RoleFeature;
import org.springframework.stereotype.Repository;

/**
 * 角色权限关系表(SecRoleFeature)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:32
 */
@Repository
public interface RoleFeatureDao extends BaseRelationDao<RoleFeature, Role, Feature> {

}