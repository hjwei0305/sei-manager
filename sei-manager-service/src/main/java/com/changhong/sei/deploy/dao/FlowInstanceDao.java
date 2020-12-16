package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.FlowInstance;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 * 流程实例(FlowInstance)数据库访问类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Repository
public interface FlowInstanceDao extends BaseEntityDao<FlowInstance> {

    /**
     * 按申请单id删除任务
     *
     * @param orderId 申请单id
     * @return 删除记录数
     */
    @Modifying
    int deleteByOrderId(String orderId);
}