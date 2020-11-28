package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.deploy.entity.FlowDefinition;
import com.changhong.sei.deploy.entity.FlowTask;
import com.changhong.sei.deploy.entity.FlowType;
import org.springframework.stereotype.Repository;

/**
 * 流程定义(FlowDefinition)数据库访问类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Repository
public interface FlowDefinitionDao extends BaseRelationDao<FlowDefinition, FlowType, FlowTask> {

}