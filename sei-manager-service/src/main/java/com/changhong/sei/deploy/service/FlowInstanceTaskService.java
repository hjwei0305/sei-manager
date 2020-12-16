package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowInstanceTaskDao;
import com.changhong.sei.deploy.entity.FlowInstanceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}