package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.DeployTemplateStageDao;
import com.changhong.sei.deploy.dto.DeployTemplateStageResponse;
import com.changhong.sei.deploy.entity.DeployStage;
import com.changhong.sei.deploy.entity.DeployTemplate;
import com.changhong.sei.deploy.entity.DeployTemplateStage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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
                stage = stageService.findOne(stage.getId());
                templateStage.setPlayscript(stage.getPlayscript());
            }
        }
        super.save(entities);
    }

    /**
     * 创建分配关系
     *
     * @param parentId 父实体Id
     * @param childIds 子实体Id清单
     * @return 操作结果
     */
    @Override
    public OperateResult insertRelations(String parentId, List<String> childIds) {
        if (childIds == null || childIds.size() == 0) {
            return OperateResult.operationSuccess("core_service_00035", 0);
        }
        //排除已经存在的分配关系
        List<DeployStage> children = getChildrenFromParentId(parentId);
        Set<String> existChildIds = new HashSet<>();
        children.forEach((c) -> existChildIds.add(c.getId()));
        Set<String> addChildIds = new HashSet<>(childIds);
        addChildIds.removeAll(existChildIds);

        int sort = existChildIds.size();
        //创建需要创建的分配关系
        List<DeployTemplateStage> relations = new ArrayList<>();
        for (String c : addChildIds) {
            DeployTemplateStage relation = getDao().constructRelation(parentId, c);
            if (relation != null) {
                // 追加序号
                relation.setRank(sort++);
                relations.add(relation);
            }
        }
        //提交数据库
        if (relations.size() > 0) {
            save(relations);
        }
        //成功创建{0}个分配关系！
        return OperateResult.operationSuccess("core_service_00035", relations.size());
    }

    /**
     * 通过模版Id获取阶段清单
     *
     * @param templateId 模版Id
     * @return 获取阶段清单
     */
    public ResultData<List<DeployTemplateStageResponse>> getStageByTemplateId(String templateId) {
        List<DeployTemplateStageResponse> list = new ArrayList<>();
        List<DeployTemplateStage> templateStages = this.getRelationsByParentId(templateId);
        if (CollectionUtils.isNotEmpty(templateStages)) {
            DeployStage stage;
            DeployTemplateStageResponse response;
            for (DeployTemplateStage templateStage : templateStages) {
                if (Objects.isNull(templateStage)) {
                    continue;
                }
                stage = templateStage.getChild();
                if (Objects.isNull(stage)) {
                    continue;
                }
                response = new DeployTemplateStageResponse();
                response.setTemplateId(templateId);
                response.setStageId(stage.getId());
                response.setName(stage.getName());
                response.setRemark(stage.getRemark());
                response.setPlayscript(templateStage.getPlayscript());
                response.setRank(templateStage.getRank());
                response.setId(templateStage.getId());
                list.add(response);
            }
        }
        return ResultData.success(list);
    }
}