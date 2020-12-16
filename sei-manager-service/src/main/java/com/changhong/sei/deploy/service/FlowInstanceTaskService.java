package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowInstanceTaskDao;
import com.changhong.sei.deploy.entity.FlowInstanceTask;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 通过流程类型获取节点清单
     *
     * @param instanceId 流程实例id
     * @return 返回结果
     */
    public List<FlowInstanceTask> getTypeNodeRecord(String instanceId) {
        return this.findListByProperty(FlowInstanceTask.FIELD_INSTANCE_ID, instanceId);
    }

    /**
     * 获取下一个任务节点并检查是否是最后一个
     *
     * @param instanceId 流程实例id
     * @param taskNo     任务号
     * @return 下个任务节点
     */
    public ResultData<FlowInstanceTask> getNextTaskAndCheckLast(String instanceId, int taskNo) {
        FlowInstanceTask result = null;
        List<FlowInstanceTask> nodeRecords = this.findListByProperty(FlowInstanceTask.FIELD_INSTANCE_ID, instanceId);
        if (CollectionUtils.isNotEmpty(nodeRecords)) {
            // 顺序排序,以便下一步取出最近一个
            List<FlowInstanceTask> tasks = nodeRecords.stream().sorted(Comparator.comparing(FlowInstanceTask::getRank)).collect(Collectors.toList());
            for (FlowInstanceTask task : tasks) {
                if (taskNo < task.getRank()) {
                    result = task;
                    break;
                }
            }
            // 如果通过类型及版本找到有对应的任务,但通过任务号未匹配上,则认为当前任务是最后一个任务,流程应结束
            return ResultData.success(result);
        } else {
            return ResultData.fail("没有找到对应的流程任务节点.");
        }
    }
}