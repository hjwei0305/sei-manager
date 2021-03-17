package com.changhong.sei.cicd.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.cicd.entity.FlowInstanceTask;
import org.springframework.stereotype.Repository;

/**
 * 流程实例任务节点(FlowInstanceTask)数据库访问类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Repository
public interface FlowInstanceTaskDao extends BaseEntityDao<FlowInstanceTask> {

}