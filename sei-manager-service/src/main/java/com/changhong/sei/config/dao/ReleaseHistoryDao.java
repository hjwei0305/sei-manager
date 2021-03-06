package com.changhong.sei.config.dao;

import com.changhong.sei.config.entity.ReleaseHistory;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 发布历史(ConfReleaseHistory)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:44:11
 */
@Repository
public interface ReleaseHistoryDao extends BaseEntityDao<ReleaseHistory> {

    /**
     * 应用环境配置发布版本
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 返回配置发布版本
     */
    @Query("select h.version from ReleaseHistory h where h.appCode = :appCode and h.envCode = :envCode group by h.version")
    Set<String> getVersions(@Param("appCode") String appCode, @Param("envCode") String envCode);

    /**
     * 获取上一次发布的配置项
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 返回上一次发布的配置项
     */
    @Query("select h from ReleaseHistory h where h.appCode = :appCode and h.envCode = :envCode and h.version = " +
            " (select c.version from ReleasedConfig c where c.appCode = :appCode and c.envCode = :envCode group by c.version)")
    List<ReleaseHistory> getLastReleaseHistory(@Param("appCode") String appCode, @Param("envCode") String envCode);
}