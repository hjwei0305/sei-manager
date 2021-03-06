package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.ReleasedConfigDao;
import com.changhong.sei.config.entity.ReleasedConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 已发布的应用配置(ConfReleasedConfig)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:44:17
 */
@Service("releasedConfigService")
public class ReleasedConfigService extends BaseEntityService<ReleasedConfig> {
    @Autowired
    private ReleasedConfigDao dao;

    @Override
    protected BaseEntityDao<ReleasedConfig> getDao() {
        return dao;
    }

    /**
     * 根据环境和应用代码删除配置
     *
     * @param envCode 环境代码
     * @param appCode 应用代码
     */
    public void removeByEnvAppCode(String envCode, String appCode) {
        dao.removeByEnvAppCode(envCode, appCode);
    }
}