package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.AppConfigDao;
import com.changhong.sei.config.entity.AppConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 应用参数配置(ConfAppConfig)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:43:46
 */
@Service("appConfigService")
public class AppConfigService extends BaseEntityService<AppConfig> {
    @Autowired
    private AppConfigDao dao;

    @Override
    protected BaseEntityDao<AppConfig> getDao() {
        return dao;
    }

}