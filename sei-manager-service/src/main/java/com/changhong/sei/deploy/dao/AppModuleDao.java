package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.AppModule;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 应用模块(AppModule)数据库访问类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Repository
public interface AppModuleDao extends BaseEntityDao<AppModule> {

    /**
     * 更新版本号
     *
     * @return 返回更新的记录数
     */
    @Modifying
    @Query("update AppModule t set t.version = :version where t.gitId = :gitId ")
    int updateVersion(@Param("gitId") String gitId, @Param("version") String version);

}