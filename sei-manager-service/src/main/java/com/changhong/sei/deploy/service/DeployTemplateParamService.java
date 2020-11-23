package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.DeployTemplateParamDao;
import com.changhong.sei.deploy.entity.DeployTemplateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 部署模板参数(DeployTemplateParam)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:05
 */
@Service("deployTemplateParamService")
public class DeployTemplateParamService extends BaseEntityService<DeployTemplateParam> {
    @Autowired
    private DeployTemplateParamDao dao;

    @Override
    protected BaseEntityDao<DeployTemplateParam> getDao() {
        return dao;
    }

}