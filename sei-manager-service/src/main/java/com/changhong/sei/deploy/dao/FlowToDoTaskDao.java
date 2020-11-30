package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.ApplicationRequisition;
import com.changhong.sei.deploy.entity.FlowToDoTask;
import org.springframework.stereotype.Repository;

/**
 * 待办任务视图(FlowToDoTask)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface FlowToDoTaskDao extends BaseEntityDao<FlowToDoTask> {

}