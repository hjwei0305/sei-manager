package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowTypeNodeRecordDao;
import com.changhong.sei.deploy.entity.FlowType;
import com.changhong.sei.deploy.entity.FlowTypeNodeRecord;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 流程类型节点记录(FlowTypeNodeRecord)业务逻辑实现类
 *
 * @author sei
 * @since 2020-12-16 12:49:10
 */
@Service("flowTypeNodeRecordService")
public class FlowTypeNodeRecordService extends BaseEntityService<FlowTypeNodeRecord> {
    @Autowired
    private FlowTypeNodeRecordDao dao;
    @Autowired
    private FlowTypeService typeService;

    @Override
    protected BaseEntityDao<FlowTypeNodeRecord> getDao() {
        return dao;
    }

    /**
     * 根据流程类型id,获取流程类型
     *
     * @param typeId 流程类型id
     * @return 流程类型
     */
    public FlowType getFlowType(String typeId) {
        return typeService.findOne(typeId);
    }

    /**
     * 根据流程类型id,获取当前最新实例的版本
     *
     * @param flowTypeId 流程类型id
     * @return 最新实例的版本
     */
    public Integer getLatestVersion(String flowTypeId) {
        return dao.findLatestVersion(flowTypeId);
    }

    /**
     * 根据流程类型id,获取当前最新实例的版本
     *
     * @param typeId 流程类型id
     * @return 最新实例的版本
     */
    public List<FlowTypeNodeRecord> getFlowTypeNodeRecord(String typeId, Integer version) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowTypeNodeRecord.FIELD_TYPE_ID, typeId));
        search.addFilter(new SearchFilter(FlowTypeNodeRecord.FIELD_VERSION, version));
        return dao.findByFilters(search);
    }

    /**
     * 获取下一个任务节点并检查是否是最后一个
     *
     * @param flowTypeId 类型id
     * @param version    版本
     * @param taskNo     任务号
     * @return 下个任务节点
     */
    public ResultData<FlowTypeNodeRecord> getNextTaskAndCheckLast(String flowTypeId, long version, int taskNo) {
        FlowTypeNodeRecord result = null;
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowTypeNodeRecord.FIELD_TYPE_ID, flowTypeId));
        search.addFilter(new SearchFilter(FlowTypeNodeRecord.FIELD_VERSION, version));
        List<FlowTypeNodeRecord> nodeRecords = this.findByFilters(search);
        if (CollectionUtils.isNotEmpty(nodeRecords)) {
            // 顺序排序,以便下一步取出最近一个
            List<FlowTypeNodeRecord> tasks = nodeRecords.stream().sorted(Comparator.comparing(FlowTypeNodeRecord::getRank)).collect(Collectors.toList());
            for (FlowTypeNodeRecord task : tasks) {
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