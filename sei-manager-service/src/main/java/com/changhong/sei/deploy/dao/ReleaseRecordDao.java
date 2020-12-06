package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.dto.BuildStatus;
import com.changhong.sei.deploy.entity.ReleaseRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 发布记录(ReleaseRecord)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface ReleaseRecordDao extends BaseEntityDao<ReleaseRecord> {

    /**
     * 更新构建状态
     */
    @Modifying
    @Query("update ReleaseRecord t set t.buildStatus = :status where t.id = :id ")
    void updateByBuildStatus(@Param("id") String id, @Param("status") BuildStatus status);
}