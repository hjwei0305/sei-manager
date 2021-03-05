package com.changhong.sei.config.dao;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.entity.AppConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 应用参数配置(ConfAppConfig)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:43:46
 */
@Repository
public interface AppConfigDao extends BaseEntityDao<AppConfig> {

    /**
     * 更新配置状态
     *
     * @param ids       更新的配置id清单
     * @param useStatus 状态
     * @return 返回更新记录数
     */
    @Modifying
    @Query("update AppConfig c set c.useStatus = :useStatus where c.id in :ids")
    int updateStatus(@Param("ids") Set<String> ids, @Param("useStatus") UseStatus useStatus);
}