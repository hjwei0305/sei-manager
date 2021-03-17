package com.changhong.sei.cicd.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.cicd.dao.FlowTypeNodeDao;
import com.changhong.sei.cicd.entity.FlowTypeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


/**
 * 流程类型节点定义(FlowTypeNode)业务逻辑实现类
 *
 * @author sei
 * @since 2020-12-16 12:49:08
 */
@Service("flowTypeNodeService")
public class FlowTypeNodeService extends BaseEntityService<FlowTypeNode> {
    @Autowired
    private FlowTypeNodeDao dao;

    @Override
    protected BaseEntityDao<FlowTypeNode> getDao() {
        return dao;
    }

    /**
     * 保存流程类型节点
     *
     * @param typeNode dto
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<FlowTypeNode> saveTypeNode(FlowTypeNode typeNode) {
        OperateResultWithData<FlowTypeNode> result = this.save(typeNode);
        if (result.successful()) {
            return ResultData.success(result.getData());
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 删除流程类型节点
     *
     * @param ids 流程类型节点Id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTypeNode(Set<String> ids) {
        this.delete(ids);
    }

    /**
     * 通过流程类型获取节点清单
     *
     * @param typeId 流程类型id
     * @return 返回结果
     */
    public List<FlowTypeNode> getTypeNodeByTypeId(String typeId) {
        return dao.findListByProperty(FlowTypeNode.FIELD_TYPE_ID, typeId);
    }
}