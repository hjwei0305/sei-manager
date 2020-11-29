package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.FlowTaskHistory;
import com.changhong.sei.deploy.entity.FlowTaskInstance;
import org.springframework.stereotype.Repository;

/**
 * 流程任务历史记录(FlowTaskHistory)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface FlowTaskHistoryDao extends BaseEntityDao<FlowTaskHistory> {

}