package com.changhong.sei.deploy.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowInstanceTaskDao;
import com.changhong.sei.deploy.dto.FlowTypeNodeRecordDto;
import com.changhong.sei.deploy.entity.FlowInstanceTask;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程实例任务节点(FlowInstanceTask)业务逻辑实现类
 *
 * @author sei
 * @since 2020-12-16 12:49:06
 */
@Service("flowInstanceTaskService")
public class FlowInstanceTaskService extends BaseEntityService<FlowInstanceTask> {
    @Autowired
    private FlowInstanceTaskDao dao;

    @Override
    protected BaseEntityDao<FlowInstanceTask> getDao() {
        return dao;
    }

    public List<FlowInstanceTask> getFlowInstanceTask(String typeId, Integer version, String account) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowInstanceTask.FIELD_TYPE_ID, typeId));
        search.addFilter(new SearchFilter(FlowInstanceTask.FIELD_VERSION, version));
        search.addFilter(new SearchFilter(FlowInstanceTask.FIELD_OWNER, account));
        return dao.findByFilters(search);
    }

    /**
     * 更新任务节点
     *
     * @param nodeRecordDtos 任务节点清单
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> putFlowInstanceTask(String typeId, Integer version, List<FlowTypeNodeRecordDto> nodeRecordDtos) {
        if (CollectionUtils.isEmpty(nodeRecordDtos)) {
            return ResultData.fail("任务节点不能为空.");
        }
        String account = ContextUtil.getUserAccount();
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowInstanceTask.FIELD_TYPE_ID, typeId));
        search.addFilter(new SearchFilter(FlowInstanceTask.FIELD_VERSION, version));
        search.addFilter(new SearchFilter(FlowInstanceTask.FIELD_OWNER, account));
        List<FlowInstanceTask> taskList = getFlowInstanceTask(typeId, version, account);
        if (CollectionUtils.isNotEmpty(taskList)) {
            // 如果存在则删除
            dao.deleteInBatch(taskList);
        }

        taskList = new ArrayList<>();
        FlowInstanceTask task;
        for (FlowTypeNodeRecordDto record : nodeRecordDtos) {
            task = new FlowInstanceTask();
            task.setTypeId(typeId);
            task.setTypeName(record.getTypeName());
            task.setVersion(version);
            task.setCode(String.valueOf(record.getCode()));
            task.setName(record.getName());
            task.setHandleAccount(record.getHandleAccount());
            task.setHandleUserName(record.getHandleUserName());
            task.setOwner(account);
            taskList.add(task);
        }
        this.save(taskList);

        return ResultData.success();
    }
}