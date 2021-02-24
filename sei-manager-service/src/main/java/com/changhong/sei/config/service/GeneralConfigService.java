package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.GeneralConfigDao;
import com.changhong.sei.config.entity.GeneralConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 通用参数配置(GeneralConfig)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:44:04
 */
@Service("GeneralConfigService")
public class GeneralConfigService extends BaseEntityService<GeneralConfig> {
    @Autowired
    private GeneralConfigDao dao;

    @Override
    protected BaseEntityDao<GeneralConfig> getDao() {
        return dao;
    }

}