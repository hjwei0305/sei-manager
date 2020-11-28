package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowTaskDao;
import com.changhong.sei.deploy.entity.FlowTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 流程任务(FlowTask)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Service("flowTaskService")
public class FlowTaskService extends BaseEntityService<FlowTask> {
    @Autowired
    private FlowTaskDao dao;

    @Override
    protected BaseEntityDao<FlowTask> getDao() {
        return dao;
    }

}