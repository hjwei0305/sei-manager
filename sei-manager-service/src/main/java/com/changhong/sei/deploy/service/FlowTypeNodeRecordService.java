package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowTypeNodeRecordDao;
import com.changhong.sei.deploy.entity.FlowTypeNodeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    @Override
    protected BaseEntityDao<FlowTypeNodeRecord> getDao() {
        return dao;
    }

}