package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.DeployTemplateParam;
import org.springframework.stereotype.Repository;

/**
 * 部署模板参数(DeployTemplateParam)数据库访问类
 *
 * @author sei
 * @since 2020-11-23 08:34:05
 */
@Repository
public interface DeployTemplateParamDao extends BaseEntityDao<DeployTemplateParam> {

}