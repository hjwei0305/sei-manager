package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.ReleaseVersionDao;
import com.changhong.sei.deploy.entity.ReleaseVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 版本发布记录(ReleaseVersion)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("releaseVersionService")
public class ReleaseVersionService extends BaseEntityService<ReleaseVersion> {
    @Autowired
    private ReleaseVersionDao dao;

    @Override
    protected BaseEntityDao<ReleaseVersion> getDao() {
        return dao;
    }

}