package com.changhong.sei.config.dao;

import com.changhong.sei.config.entity.GeneralConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * 全局参数配置(ConfGlobalConfig)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:44:04
 */
@Repository
public interface GeneralConfigDao extends BaseEntityDao<GeneralConfig> {

}