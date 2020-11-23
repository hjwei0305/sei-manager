package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.manager.entity.DeployStage;
import org.springframework.stereotype.Repository;

/**
 * 部署阶段(DeployStage)数据库访问类
 *
 * @author sei
 * @since 2020-11-23 08:34:01
 */
@Repository
public interface DeployStageDao extends BaseEntityDao<DeployStage> {

}