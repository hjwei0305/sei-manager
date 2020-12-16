package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.FlowTypeNodeRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 流程类型节点记录(FlowTypeNodeRecord)数据库访问类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Repository
public interface FlowTypeNodeRecordDao extends BaseEntityDao<FlowTypeNodeRecord> {

    /**
     * 根据流程类型id,获取当前最新实例的版本
     *
     * @param typeId 流程类型id
     * @return 最新实例的版本
     */
    @Query("select max(t.version) from FlowTypeNodeRecord t where t.typeId = :typeId")
    Integer findLatestVersion(@Param("typeId") String typeId);
}