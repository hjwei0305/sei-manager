package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.AppModule;
import org.springframework.stereotype.Repository;

/**
 * 应用模块(AppModule)数据库访问类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Repository
public interface AppModuleDao extends BaseEntityDao<AppModule> {

}