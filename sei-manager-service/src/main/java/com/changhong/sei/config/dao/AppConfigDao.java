package com.changhong.sei.config.dao;

import com.changhong.sei.config.entity.AppConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * 应用参数配置(ConfAppConfig)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:43:46
 */
@Repository
public interface AppConfigDao extends BaseEntityDao<AppConfig> {

}