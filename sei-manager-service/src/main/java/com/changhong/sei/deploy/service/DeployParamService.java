package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.DeployParamDao;
import com.changhong.sei.deploy.entity.DeployParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 部署参数(DeployParam)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:59
 */
@Service("deployParamService")
public class DeployParamService extends BaseEntityService<DeployParam> {
    @Autowired
    private DeployParamDao dao;

    @Override
    protected BaseEntityDao<DeployParam> getDao() {
        return dao;
    }

}