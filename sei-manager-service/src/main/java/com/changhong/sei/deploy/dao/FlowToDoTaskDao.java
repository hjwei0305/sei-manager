package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.FlowToDoTask;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 待办任务视图(FlowToDoTask)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface FlowToDoTaskDao extends BaseEntityDao<FlowToDoTask> {
    /**
     * 按申请单id删除任务
     *
     * @param orderId 申请单id
     * @return 删除记录数
     */
    @Modifying
    int deleteByOrderId(String orderId);

    /**
     * 按申请单id更新待办状态
     *
     * @param pending 待办
     * @param orderId 申请单id
     * @return 删除记录数
     */
    @Modifying
    @Query("update FlowToDoTask t set t.pending = :pending where t.orderId = :orderId")
    int updateStatus(@Param("pending") Boolean pending, @Param("orderId") String orderId);
}