package com.changhong.sei.config.dao;

import com.changhong.sei.config.entity.ReleasedConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 已发布的应用配置(ConfReleasedConfig)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:44:17
 */
@Repository
public interface ReleasedConfigDao extends BaseEntityDao<ReleasedConfig> {

    /**
     * 根据环境和应用代码删除配置
     *
     * @param envCode 环境代码
     * @param appCode 应用代码
     * @return 处理记录数
     */
    @Modifying
    @Query("delete from ReleasedConfig rc where rc.envCode = :envCode and rc.appCode = :appCode")
    int removeByEnvAppCode(@Param("envCode") String envCode, @Param("appCode") String appCode);
}