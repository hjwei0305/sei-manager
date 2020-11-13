package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.manager.entity.Feature;
import org.springframework.stereotype.Repository;

/**
 * 权限表(SecPermission)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:28
 */
@Repository
public interface FeatureDao extends BaseEntityDao<Feature> {

}