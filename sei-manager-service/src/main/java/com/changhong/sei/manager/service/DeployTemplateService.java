package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.dao.DeployTemplateDao;
import com.changhong.sei.manager.entity.DeployTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 部署模板(DeployTemplate)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:03
 */
@Service("deployTemplateService")
public class DeployTemplateService extends BaseEntityService<DeployTemplate> {
    @Autowired
    private DeployTemplateDao dao;

    @Override
    protected BaseEntityDao<DeployTemplate> getDao() {
        return dao;
    }

}