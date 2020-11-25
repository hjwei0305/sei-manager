package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.DeployTemplateStageDao;
import com.changhong.sei.deploy.entity.DeployStage;
import com.changhong.sei.deploy.entity.DeployTemplate;
import com.changhong.sei.deploy.entity.DeployTemplateStage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


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
    @Autowired
    private DeployStageService stageService;

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
        return stageService.findAllUnfrozen();
    }

    /**
     * 数据保存操作
     */
    @Override
    public OperateResultWithData<DeployTemplateStage> save(DeployTemplateStage entity) {
        String id = entity.getId();
        if (StringUtils.isNotBlank(id)) {
            DeployTemplateStage templateStage = this.findOne(id);
            if (Objects.nonNull(templateStage)) {
                templateStage.setPlayscript(entity.getPlayscript());
                templateStage.setRank(entity.getRank());

                return super.save(templateStage);
            }
        }
        return OperateResultWithData.operationFailure("修改的模板阶段不存在.");
    }

    /**
     * 批量数据保存操作 其实现只是简单循环集合每个元素调用
     * 因此并无实际的Batch批量处理，如果需要数据库底层批量支持请自行实现
     *
     * @param entities 待批量操作数据集合
     */
    @Override
    public void save(Collection<DeployTemplateStage> entities) {
        DeployStage stage;
        for (DeployTemplateStage templateStage : entities) {
            if (Objects.isNull(templateStage)) {
                continue;
            }
            stage = templateStage.getChild();
            if (Objects.nonNull(stage)) {
                templateStage.setPlayscript(stage.getPlayscript());
            }
        }
        super.save(entities);
    }
}