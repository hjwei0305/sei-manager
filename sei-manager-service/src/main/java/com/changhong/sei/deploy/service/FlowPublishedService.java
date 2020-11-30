package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowPublishedDao;
import com.changhong.sei.deploy.entity.FlowPublished;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 流程类型任务(FlowTypeTask)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Service("flowTypeTaskService")
public class FlowPublishedService extends BaseEntityService<FlowPublished> {
    @Autowired
    private FlowPublishedDao dao;

    @Override
    protected BaseEntityDao<FlowPublished> getDao() {
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
     * 获取指定任务
     *
     * @param flowTypeId 类型id
     * @param version    版本
     * @param taskId     任务id
     * @return 下个任务
     */
    public ResultData<FlowPublished> getTask(String flowTypeId, long version, String taskId) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowPublished.FIELD_TYPE_ID, flowTypeId));
        search.addFilter(new SearchFilter(FlowPublished.FIELD_VERSION, version));
        search.addFilter(new SearchFilter(FlowPublished.FIELD_TASK_ID, taskId));
        FlowPublished task = this.findFirstByFilters(search);
        if (Objects.isNull(task)) {
            return ResultData.fail("任务不存在!");
        } else {
            return ResultData.success(task);
        }
    }

    /**
     * 获取下一个任务并检查是否是最后一个
     *
     * @param flowTypeId 类型id
     * @param version    版本
     * @param taskNo     任务号
     * @return 下个任务
     */
    public ResultData<FlowPublished> getNextTaskAndCheckLast(String flowTypeId, long version, int taskNo) {
        FlowPublished result = null;
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowPublished.FIELD_TYPE_ID, flowTypeId));
        search.addFilter(new SearchFilter(FlowPublished.FIELD_VERSION, version));
        List<FlowPublished> taskInstances = this.findByFilters(search);
        if (CollectionUtils.isNotEmpty(taskInstances)) {
            // 顺序排序,以便下一步取出最近一个
            List<FlowPublished> tasks = taskInstances.stream().sorted(Comparator.comparing(FlowPublished::getRank)).collect(Collectors.toList());
            for (FlowPublished task : tasks) {
                if (taskNo < task.getRank()) {
                    result = task;
                    break;
                }
            }
            // 如果通过类型及版本找到有对应的任务,但通过任务号未匹配上,则认为当前任务是最后一个任务,流程应结束
            return ResultData.success(result);
        } else {
            return ResultData.fail("没有找到对应的流程任务.");
        }
    }
}