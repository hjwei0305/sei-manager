package com.changhong.sei.deploy.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.FlowTypeDao;
import com.changhong.sei.deploy.entity.FlowInstance;
import com.changhong.sei.deploy.entity.FlowInstanceTask;
import com.changhong.sei.deploy.entity.FlowType;
import com.changhong.sei.deploy.entity.FlowTypeNode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 流程类型(FlowType)业务逻辑实现类
 *
 * @author sei
 * @since 2020-12-16 12:49:06
 */
@Service("flowTypeService")
public class FlowTypeService extends BaseEntityService<FlowType> {
    @Autowired
    private FlowTypeDao dao;

    @Autowired
    private FlowTypeNodeService nodeService;
    @Autowired
    private FlowInstanceService instanceService;
    @Autowired
    private FlowInstanceTaskService instanceTaskService;

    @Override
    protected BaseEntityDao<FlowType> getDao() {
        return dao;
    }

    /**
     * 根据流程类型id,获取流程类型
     *
     * @param typeCode 流程类型代码
     * @return 流程类型
     */
    public FlowType getFlowType(String typeCode) {
        return dao.findByProperty(FlowType.CODE_FIELD, typeCode);
    }

    /**
     * 保存流程类型
     *
     * @param flowType type
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<FlowType> saveType(FlowType flowType) {
        OperateResultWithData<FlowType> result = this.save(flowType);
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
        return dao.findByPage(search);
    }

    /**
     * 获取能再定义的流程类型
     *
     * @return 分页数据结果
     */
    public List<FlowType> findRedefinedTypes() {
        return dao.findListByProperty(FlowType.FIELD_REDEFINED, Boolean.TRUE);
    }


    /**
     * 发布流程类型
     *
     * @param typeId 流程类型id
     * @return 发布结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> publish(String typeId) {
        FlowType type = dao.findOne(typeId);
        if (Objects.isNull(type)) {
            return ResultData.fail("流程类型[" + typeId + "]不存在");
        }
        List<FlowTypeNode> nodeList = nodeService.getTypeNodeByTypeId(typeId);
        if (CollectionUtils.isEmpty(nodeList)) {
            return ResultData.fail(type.getName() + "-未配置任务节点.");
        }

        // 增加版本号
        int version = type.getVersion() + 1;

        // 发布时间
        LocalDateTime publishedTime = LocalDateTime.now();
        // 发布人账号
        String publishedAccount = ContextUtil.getUserAccount();
        // 写入流程类型版本记录
        FlowInstance instance = new FlowInstance();
        instance.setCode(type.getCode());
        instance.setName(type.getName());
        instance.setVersion(version);
        instance.setRemark(type.getRemark());
        // 更新发布状态
        instance.setPublished(Boolean.TRUE);
        instance.setPublishedTime(publishedTime);
        instance.setPublishedAccount(publishedAccount);
        OperateResultWithData<FlowInstance> result = instanceService.save(instance);
        if (result.successful()) {
            String instanceId = instance.getId();
            // 写入流程类型节点记录
            FlowInstanceTask task;
            List<FlowInstanceTask> nodeRecords = new ArrayList<>(nodeList.size());
            for (FlowTypeNode node : nodeList) {
                task = new FlowInstanceTask();
                task.setInstanceId(instanceId);
                task.setCode(node.getCode());
                task.setName(node.getName());
                task.setHandleAccount(node.getHandleAccount());
                task.setHandleUserName(node.getHandleUserName());
                nodeRecords.add(task);
            }
            instanceTaskService.save(nodeRecords);

            // 更新版本号
            type.setVersion(version);
            type.setPublishedTime(publishedTime);
            type.setPublishedAccount(publishedAccount);
            OperateResultWithData<FlowType> result1 = this.save(type);
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