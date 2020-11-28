package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowTaskInstanceDao;
import com.changhong.sei.deploy.entity.FlowTaskInstance;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 流程实例(FlowInstance)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Service("flowTaskInstanceService")
public class FlowTaskInstanceService extends BaseEntityService<FlowTaskInstance> {
    @Autowired
    private FlowTaskInstanceDao dao;

    @Override
    protected BaseEntityDao<FlowTaskInstance> getDao() {
        return dao;
    }

    /**
     * 根据流程类型id,获取当前最新实例的版本
     *
     * @param flowTypeId 流程类型id
     * @return 最新实例的版本
     */
    public Long getLatestVersion(String flowTypeId) {
        return dao.findLatestVersion(flowTypeId);
    }

    /**
     * 获取下一个任务
     *
     * @param flowTypeId 类型id
     * @param version    版本
     * @param taskNo     任务号
     * @return 下个任务
     */
    public FlowTaskInstance getNextTask(String flowTypeId, long version, Integer taskNo) {
        FlowTaskInstance result = null;
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowTaskInstance.FIELD_TYPE_ID, flowTypeId));
        search.addFilter(new SearchFilter(FlowTaskInstance.FIELD_VERSION, version));
        List<FlowTaskInstance> taskInstances = this.findByFilters(search);
        if (CollectionUtils.isNotEmpty(taskInstances)) {
            List<FlowTaskInstance> tasks = taskInstances.stream().sorted(Comparator.comparing(FlowTaskInstance::getRank)).collect(Collectors.toList());
            result = tasks.get(0);
            if (Objects.nonNull(taskNo)) {
                for (FlowTaskInstance task : tasks) {
                    if (taskNo < task.getRank()) {
                        result = task;
                        break;
                    }
                }
            }
        }
        return result;
    }
}