package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.DeployConfigDao;
import com.changhong.sei.deploy.entity.DeployConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 部署配置(DeployConfig)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("deployConfigService")
public class DeployConfigService extends BaseEntityService<DeployConfig> {
    @Autowired
    private DeployConfigDao dao;

    @Override
    protected BaseEntityDao<DeployConfig> getDao() {
        return dao;
    }

}