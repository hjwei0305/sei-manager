package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.deploy.dao.DeployTemplateStageDao;
import com.changhong.sei.deploy.entity.DeployStage;
import com.changhong.sei.deploy.entity.DeployTemplate;
import com.changhong.sei.deploy.entity.DeployTemplateStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 部署模板阶段关系表(DeployTemplateStage)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:07
 */
@Service("deployTemplateStageService")
public class DeployTemplateStageService extends BaseRelationService<DeployTemplateStage, DeployTemplate, DeployStage> {
    @Autowired
    private DeployTemplateStageDao dao;

    @Override
    protected BaseRelationDao<DeployTemplateStage, DeployTemplate, DeployStage> getDao() {
        return dao;
    }

    /**
     * 获取可以分配的子实体清单
     *
     * @param parentId
     * @return 子实体清单
     */
    @Override
    protected List<DeployStage> getCanAssignedChildren(String parentId) {
        return null;
    }
}