package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.FlowTaskHistory;
import com.changhong.sei.deploy.entity.FlowTaskInstance;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 * 流程任务历史记录(FlowTaskHistory)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface FlowTaskHistoryDao extends BaseEntityDao<FlowTaskHistory> {

    /**
     * 按申请单id删除任务
     *
     * @param orderId 申请单id
     * @return 删除记录数
     */
    @Modifying
    int deleteByOrderId(String orderId);
}