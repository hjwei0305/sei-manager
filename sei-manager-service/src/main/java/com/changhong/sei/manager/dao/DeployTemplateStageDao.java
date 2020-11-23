package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.manager.entity.DeployStage;
import com.changhong.sei.manager.entity.DeployTemplate;
import com.changhong.sei.manager.entity.DeployTemplateStage;
import org.springframework.stereotype.Repository;

/**
 * 部署模板阶段关系表(DeployTemplateStage)数据库访问类
 *
 * @author sei
 * @since 2020-11-23 08:34:07
 */
@Repository
public interface DeployTemplateStageDao extends BaseRelationDao<DeployTemplateStage, DeployTemplate, DeployStage> {

}