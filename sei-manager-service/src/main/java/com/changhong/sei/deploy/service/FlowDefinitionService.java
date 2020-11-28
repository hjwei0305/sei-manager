package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.deploy.dao.FlowDefinitionDao;
import com.changhong.sei.deploy.entity.FlowDefinition;
import com.changhong.sei.deploy.entity.FlowTask;
import com.changhong.sei.deploy.entity.FlowType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 流程定义(FlowDefinition)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Service("flowDefinitionService")
public class FlowDefinitionService extends BaseRelationService<FlowDefinition, FlowType, FlowTask> {
    @Autowired
    private FlowDefinitionDao dao;
    @Autowired
    private FlowTaskService flowTaskService;

    @Override
    protected BaseRelationDao<FlowDefinition, FlowType, FlowTask> getDao() {
        return dao;
    }

    /**
     * 获取可以分配的子实体清单
     *
     * @param parentId
     * @return 子实体清单
     */
    @Override
    protected List<FlowTask> getCanAssignedChildren(String parentId) {
        return flowTaskService.findAllUnfrozen();
    }
}