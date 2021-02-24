package com.changhong.sei.config.dao;

import com.changhong.sei.config.entity.ReleasedConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * 已发布的应用配置(ConfReleasedConfig)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:44:17
 */
@Repository
public interface ReleasedConfigDao extends BaseEntityDao<ReleasedConfig> {

}