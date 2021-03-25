package com.changhong.sei.ge.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.ge.entity.AppModule;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    @Query("update AppModule t set t.version = :version where t.id = :moduleId ")
    int updateVersion(@Param("moduleId") String moduleId, @Param("version") String version);

    /**
     * 通过分组代码查询
     *
     * @param groupCode 分组代码
     * @return 返回应用模块记录
     */
    @Query("select m from AppModule m where m.frozen = false and m.appId in (select a.id from Application a where a.groupCode = :groupCode ) ")
    List<AppModule> getByGroup(@Param("groupCode") String groupCode);
}