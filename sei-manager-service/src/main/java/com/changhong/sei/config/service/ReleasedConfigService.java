package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.ReleasedConfigDao;
import com.changhong.sei.config.entity.ReleasedConfig;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
     * 根据环境和应用代码获取配置
     *
     * @param appCode 应用代码
     * @param profile 环境代码
     */
    public List<ReleasedConfig> getConfigs(String appCode, String profile, String label) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(ReleasedConfig.FIELD_APP_CODE, appCode));
        search.addFilter(new SearchFilter(ReleasedConfig.FIELD_ENV_CODE, profile));
        if (StringUtils.isNotBlank(label)) {
            search.addFilter(new SearchFilter(ReleasedConfig.FIELD_VERSION, label));
        }
        return dao.findByFilters(search);
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