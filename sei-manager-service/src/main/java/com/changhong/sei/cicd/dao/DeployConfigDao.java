package com.changhong.sei.cicd.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.cicd.entity.DeployConfig;
import org.springframework.stereotype.Repository;

/**
 * 部署配置(DeployConfig)数据库访问类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Repository
public interface DeployConfigDao extends BaseEntityDao<DeployConfig> {

}