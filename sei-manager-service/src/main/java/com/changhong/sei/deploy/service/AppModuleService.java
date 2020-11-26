package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.AppModuleDao;
import com.changhong.sei.deploy.entity.AppModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 应用模块(AppModule)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Service("appModuleService")
public class AppModuleService extends BaseEntityService<AppModule> {
    @Autowired
    private AppModuleDao dao;

    @Override
    protected BaseEntityDao<AppModule> getDao() {
        return dao;
    }

}