package com.changhong.sei.config.dao;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.entity.GeneralConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 全局参数配置(ConfGlobalConfig)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:44:04
 */
@Repository
public interface GeneralConfigDao extends BaseEntityDao<GeneralConfig> {

    /**
     * 更新配置状态
     *
     * @param ids       更新的配置id清单
     * @param useStatus 状态
     * @return 返回更新记录数
     */
    @Modifying
    @Query("update GeneralConfig c set c.useStatus = :useStatus where c.id in :ids")
    int updateStatus(@Param("ids") Set<String> ids, @Param("useStatus") UseStatus useStatus);
}