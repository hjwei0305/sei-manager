package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.dao.DeployStageDao;
import com.changhong.sei.manager.entity.DeployStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 部署阶段(DeployStage)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:01
 */
@Service("deployStageService")
public class DeployStageService extends BaseEntityService<DeployStage> {
    @Autowired
    private DeployStageDao dao;

    @Override
    protected BaseEntityDao<DeployStage> getDao() {
        return dao;
    }

}