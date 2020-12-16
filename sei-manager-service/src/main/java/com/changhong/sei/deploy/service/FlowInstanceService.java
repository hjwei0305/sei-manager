package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowInstanceDao;
import com.changhong.sei.deploy.entity.FlowInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    protected BaseEntityDao<FlowInstance> getDao() {
        return dao;
    }

}