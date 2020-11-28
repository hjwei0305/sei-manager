package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.FlowTaskInstance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 流程实例(FlowInstance)数据库访问类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Repository
public interface FlowTaskInstanceDao extends BaseEntityDao<FlowTaskInstance> {

    /**
     * 根据流程类型id,获取当前最新实例的版本
     *
     * @param flowTypeId 流程类型id
     * @return 最新实例的版本
     */
    @Query("select max(t.version) from FlowTaskInstance t where t.typeId = :flowTypeId")
    Long findLatestVersion(@Param("flowTypeId") String flowTypeId);
}