package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.entity.FlowType;
import com.changhong.sei.deploy.entity.FlowTypeNode;
import com.changhong.sei.deploy.entity.FlowTypeNodeRecord;
import com.changhong.sei.deploy.entity.FlowTypeVersion;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 流程定义业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Service("flowDefinitionService")
public class FlowDefinitionService {
    @Autowired
    private FlowTypeService typeService;
    @Autowired
    private FlowTypeVersionService typeVersionService;
    @Autowired
    private FlowTypeNodeService nodeService;
    @Autowired
    private FlowTypeNodeRecordService nodeRecordService;

    /**
     * 保存流程类型
     *
     * @param flowType type
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<FlowType> saveType(FlowType flowType) {
        OperateResultWithData<FlowType> result = typeService.save(flowType);
        if (result.successful()) {
            return ResultData.success(result.getData());
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 分页查询流程类型
     *
     * @param search search
     * @return 分页数据结果
     */
    public PageResult<FlowType> findTypeByPage(Search search) {
        return typeService.findByPage(search);
    }

    /**
     * 保存流程类型节点
     *
     * @param typeNode dto
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<FlowTypeNode> saveTypeNode(FlowTypeNode typeNode) {
        OperateResultWithData<FlowTypeNode> result = nodeService.save(typeNode);
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
        nodeService.delete(ids);
    }

    /**
     * 通过流程类型获取节点清单
     *
     * @param typeId 流程类型id
     * @return 返回结果
     */
    public List<FlowTypeNode> getTypeNodeByTypeId(String typeId) {
        return nodeService.findListByProperty(FlowTypeNode.FIELD_TYPE_ID, typeId);
    }

    /**
     * 通过流程类型获取版本清单
     *
     * @param typeId 流程类型id
     * @return 返回结果
     */
    public List<FlowTypeVersion> getTypeVersionByTypeId(String typeId) {
        return typeVersionService.findListByProperty(FlowTypeVersion.FIELD_TYPE_ID, typeId);
    }

    /**
     * 通过流程类型获取节点清单
     *
     * @param typeId  流程类型id
     * @param version 流程类型版本
     * @return 返回结果
     */
    public List<FlowTypeNodeRecord> getTypeNodeRecord(String typeId, Integer version) {
        Search search = new Search();
        search.addFilter(new SearchFilter(FlowTypeVersion.FIELD_TYPE_ID, typeId));
        search.addFilter(new SearchFilter(FlowTypeVersion.FIELD_VERSION, version));
        return nodeRecordService.findByFilters(search);
    }

    /**
     * 发布流程类型
     *
     * @param typeId 流程类型id
     * @return 发布结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> publish(String typeId) {
        FlowType type = typeService.findOne(typeId);
        if (Objects.isNull(type)) {
            return ResultData.fail("流程类型[" + typeId + "]不存在");
        }
        List<FlowTypeNode> nodeList = this.getTypeNodeByTypeId(typeId);
        if (CollectionUtils.isEmpty(nodeList)) {
            return ResultData.fail(type.getName() + "-未配置任务节点.");
        }

        // 增加版本号
        int version = type.getVersion() + 1;

        // 写入流程类型节点记录
        FlowTypeNodeRecord record;
        List<FlowTypeNodeRecord> nodeRecords = new ArrayList<>(nodeList.size());
        for (FlowTypeNode node : nodeList) {
            record = new FlowTypeNodeRecord();
            record.setTypeId(typeId);
            record.setVersion(version);
            record.setCode(node.getCode());
            record.setName(node.getName());
            record.setHandleAccount(node.getHandleAccount());
            record.setHandleUserName(node.getHandleUserName());
            record.setRemark(node.getRemark());
            nodeRecords.add(record);
        }
        nodeRecordService.save(nodeRecords);

        // 写入流程类型版本记录
        FlowTypeVersion typeVersion = new FlowTypeVersion();
        typeVersion.setTypeId(typeId);
        typeVersion.setVersion(version);
        typeVersion.setName(type.getName());
        typeVersion.setRemark(type.getRemark());
        OperateResultWithData<FlowTypeVersion> result = typeVersionService.save(typeVersion);
        if (result.successful()) {
            // 更新版本号
            type.setVersion(version);
            OperateResultWithData<FlowType> result1 = typeService.save(type);
            if (result1.notSuccessful()) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail(result1.getMessage());
            }
        } else {
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultData.fail(result.getMessage());
        }

        return ResultData.success();
    }
}