package com.changhong.sei.deploy.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.FlowTypeDao;
import com.changhong.sei.deploy.entity.FlowType;
import com.changhong.sei.deploy.entity.FlowTypeNode;
import com.changhong.sei.deploy.entity.FlowTypeNodeRecord;
import com.changhong.sei.deploy.entity.FlowTypeVersion;
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
    private FlowTypeVersionService typeVersionService;
    @Autowired
    private FlowTypeNodeService nodeService;
    @Autowired
    private FlowTypeNodeRecordService nodeRecordService;

    @Override
    protected BaseEntityDao<FlowType> getDao() {
        return dao;
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

        // 写入流程类型节点记录
        FlowTypeNodeRecord record;
        List<FlowTypeNodeRecord> nodeRecords = new ArrayList<>(nodeList.size());
        for (FlowTypeNode node : nodeList) {
            record = new FlowTypeNodeRecord();
            record.setTypeId(typeId);
            record.setTypeName(type.getName());
            record.setVersion(version);
            record.setCode(node.getCode());
            record.setName(node.getName());
            record.setHandleAccount(node.getHandleAccount());
            record.setHandleUserName(node.getHandleUserName());
            record.setRemark(node.getRemark());
            nodeRecords.add(record);
        }
        nodeRecordService.save(nodeRecords);

        // 发布时间
        LocalDateTime publishedTime = LocalDateTime.now();
        // 发布人账号
        String publishedAccount = ContextUtil.getUserAccount();
        // 写入流程类型版本记录
        FlowTypeVersion typeVersion = new FlowTypeVersion();
        typeVersion.setTypeId(typeId);
        typeVersion.setVersion(version);
        typeVersion.setName(type.getName());
        typeVersion.setRemark(type.getRemark());
        typeVersion.setPublishedTime(publishedTime);
        typeVersion.setPublishedAccount(publishedAccount);
        OperateResultWithData<FlowTypeVersion> result = typeVersionService.save(typeVersion);
        if (result.successful()) {
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