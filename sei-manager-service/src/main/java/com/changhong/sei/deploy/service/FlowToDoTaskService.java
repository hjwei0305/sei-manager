package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowToDoTaskDao;
import com.changhong.sei.deploy.dto.ApplyType;
import com.changhong.sei.deploy.dto.FlowToDoTaskDto;
import com.changhong.sei.deploy.entity.FlowToDoTask;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 流程待办任务(FlowToDoTask)业务逻辑实现类
 *
 * @author sei
 * @since 2020-12-16 12:49:08
 */
@Service("flowTodoTaskService")
public class FlowToDoTaskService extends BaseEntityService<FlowToDoTask> {
    @Autowired
    private FlowToDoTaskDao dao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    protected BaseEntityDao<FlowToDoTask> getDao() {
        return dao;
    }

    /**
     * 获取待办任务数
     *
     * @return 操作结果
     */
    public ResultData<Map<ApplyType, Integer>> getTodoTaskNum(String account) {
        Map<ApplyType, Integer> result = new HashMap<>();
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowToDoTask.FIELD_EXECUTE_ACCOUNT, account));
        // 待办任务
        search.addFilter(new SearchFilter(FlowToDoTask.FIELD_PENDING, Boolean.TRUE));
        List<FlowToDoTask> tasks = dao.findByFilters(search);
        if (CollectionUtils.isNotEmpty(tasks)) {
            Map<ApplyType, List<FlowToDoTask>> map = tasks.stream().collect(Collectors.groupingBy(FlowToDoTask::getApplyType));
            for (Map.Entry<ApplyType, List<FlowToDoTask>> entry : map.entrySet()) {
                result.put(entry.getKey(), entry.getValue().size());
            }
        }
        return ResultData.success(result);
    }

    /**
     * 获取待办任务
     *
     * @return 操作结果
     */
    public ResultData<List<FlowToDoTaskDto>> getTodoTasks(String account, ApplyType applyType) {
        List<FlowToDoTaskDto> dtoList;
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(FlowToDoTask.FIELD_EXECUTE_ACCOUNT, account));
        if (Objects.nonNull(applyType)) {
            search.addFilter(new SearchFilter(FlowToDoTask.FIELD_APPLY_TYPE, applyType));
        }
        // 待办任务
        search.addFilter(new SearchFilter(FlowToDoTask.FIELD_PENDING, Boolean.TRUE));
        List<FlowToDoTask> tasks = dao.findByFilters(search);
        if (CollectionUtils.isNotEmpty(tasks)) {
            dtoList = tasks.stream().map(e -> modelMapper.map(e, FlowToDoTaskDto.class)).collect(Collectors.toList());
        } else {
            dtoList = new ArrayList<>();
        }
        return ResultData.success(dtoList);
    }

    /**
     * 按申请单id删除任务
     *
     * @param orderId 申请单id
     * @return 删除记录数
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteByOrderId(String orderId) {
        return dao.deleteByOrderId(orderId);
    }

    /**
     * 按申请单id更新待办状态
     *
     * @param pending 待办
     * @param orderId 申请单id
     * @return 删除记录数
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(Boolean pending, String orderId) {
        return dao.updateStatus(pending, orderId);
    }
}