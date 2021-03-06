package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.ReleaseHistoryDao;
import com.changhong.sei.config.entity.ReleaseHistory;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 发布历史(ConfReleaseHistory)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:44:11
 */
@Service("releaseHistoryService")
public class ReleaseHistoryService extends BaseEntityService<ReleaseHistory> {
    @Autowired
    private ReleaseHistoryDao dao;

    @Override
    protected BaseEntityDao<ReleaseHistory> getDao() {
        return dao;
    }

    /**
     * 应用环境配置发布版本
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 返回配置发布版本
     */
    public Set<String> getVersions(String appCode, String envCode) {
        return dao.getVersions(appCode, envCode);
    }

    /**
     * 获取上一次发布的配置项
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 返回上一次发布的配置项
     */
    public List<ReleaseHistory> getLastReleaseHistory(String appCode, String envCode) {
        return dao.getLastReleaseHistory(appCode, envCode);
    }

}