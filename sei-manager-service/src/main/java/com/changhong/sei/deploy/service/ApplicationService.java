package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.ApplicationDao;
import com.changhong.sei.deploy.entity.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 应用服务(Application)业务逻辑实现类
 *
 * @author sei
 * @since 2020-10-30 15:20:57
 */
@Service("applicationService")
public class ApplicationService extends BaseEntityService<Application> {
    @Autowired
    private ApplicationDao dao;

    @Override
    protected BaseEntityDao<Application> getDao() {
        return dao;
    }

}
