package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowInstanceDao;
import com.changhong.sei.deploy.entity.FlowInstance;
import com.changhong.sei.deploy.entity.FlowInstanceTask;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 流程任务实例(FlowTaskInstance)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Service("flowTaskInstanceService")
public class FlowInstanceService extends BaseEntityService<FlowInstance> {
    @Autowired
    private FlowInstanceDao dao;
    @Autowired
    private FlowInstanceTaskService instanceTaskService;

    @Override
    protected BaseEntityDao<FlowInstance> getDao() {
        return dao;
    }

    /**
     * 通过流程类型获取版本清单
     *
     * @param typeCode 流程类型code
     * @return 返回结果
     */
    public ResultData<FlowInstance> getFlowInstance(String typeCode, Integer version, String relation) {
        if (StringUtils.isBlank(typeCode)) {
            return ResultData.fail("流程类型代码不能为空.");
        }
        if (Objects.isNull(version)) {
            return ResultData.fail("流程类型版本不能为空.");
        }

        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowInstance.FIELD_CODE, typeCode));
        search.addFilter(new SearchFilter(FlowInstance.FIELD_VERSION, version));
        if (StringUtils.isBlank(relation)
                || StringUtils.equals(FlowInstance.RELATION_EMPTY, relation)) {
            // 使用默认值
            relation = FlowInstance.RELATION_EMPTY;
            search.addFilter(new SearchFilter(FlowInstance.FIELD_RELATION, relation));
            FlowInstance instance = dao.findFirstByFilters(search);
            if (Objects.nonNull(instance)) {
                return ResultData.success(instance);
            } else {
                return ResultData.fail("未找到流程类型代码[" + typeCode + "],版本[" + version + "],关联值[" + relation + "]的流程实例.");
            }
        } else {
            search.addFilter(new SearchFilter(FlowInstance.FIELD_RELATION, relation));
            FlowInstance instance = dao.findFirstByFilters(search);
            if (Objects.nonNull(instance)) {
                return ResultData.success(instance);
            } else {
                search.clearAll();
                search.addFilter(new SearchFilter(FlowInstance.FIELD_CODE, typeCode));
                search.addFilter(new SearchFilter(FlowInstance.FIELD_VERSION, version));
                search.addFilter(new SearchFilter(FlowInstance.FIELD_RELATION, FlowInstance.RELATION_EMPTY));
                instance = dao.findFirstByFilters(search);
                if (Objects.nonNull(instance)) {
                    return ResultData.success(instance);
                } else {
                    return ResultData.fail("未找到流程类型代码[" + typeCode + "],版本[" + version + "],关联值[" + relation + "]的流程实例.");
                }
            }
        }
    }

    /**
     * 通过流程类型获取版本清单
     *
     * @param typeCode 流程类型code
     * @return 返回结果
     */
    public List<FlowInstance> getTypeVersionByTypeCode(String typeCode) {
        return dao.findListByProperty(FlowInstance.FIELD_CODE, typeCode);
    }

    /**
     * 通过流程类型,版本,关联值获取流程实例任务节点
     *
     * @param typeCode 流程类型code
     * @return 返回结果
     */
    public ResultData<List<FlowInstanceTask>> getFlowInstanceTask(String typeCode, Integer version, String relation) {
        ResultData<FlowInstance> resultData = getFlowInstance(typeCode, version, relation);
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }
        FlowInstance instance = resultData.getData();
        List<FlowInstanceTask> taskList = instanceTaskService.getTypeNodeRecord(instance.getId());
        if (CollectionUtils.isNotEmpty(taskList)) {
            return ResultData.success(taskList);
        } else {
            return ResultData.fail("未配置流程类型[" + typeCode + "].");
        }
    }

    /**
     * 保存更新流程实例任务节点
     *
     * @param taskList 流程实例任务节点
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> saveFlowInstanceTask(String relation, String instanceId, List<FlowInstanceTask> taskList) {
        FlowInstance instance = dao.findOne(instanceId);
        if (Objects.isNull(instance)) {
            return ResultData.fail("流程类型实例[" + instanceId + "]不存在ø.");
        }
        FlowInstance flowInstance = new FlowInstance();
        flowInstance.setCode(instance.getCode());
        flowInstance.setName(instance.getName());
        flowInstance.setVersion(instance.getVersion());
        flowInstance.setRelation(relation);
        flowInstance.setRemark(instance.getRemark());
        this.save(flowInstance);

        String newInstanceId = flowInstance.getId();
        for (FlowInstanceTask task : taskList) {
            task.setId(null);
            task.setInstanceId(newInstanceId);
        }
        instanceTaskService.save(taskList);

        return ResultData.success();
    }
}