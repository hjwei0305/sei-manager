package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowToDoTaskDao;
import com.changhong.sei.deploy.entity.FlowToDoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    @Override
    protected BaseEntityDao<FlowToDoTask> getDao() {
        return dao;
    }

}