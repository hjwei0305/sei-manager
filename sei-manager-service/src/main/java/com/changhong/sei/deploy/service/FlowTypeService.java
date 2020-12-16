package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowTypeDao;
import com.changhong.sei.deploy.entity.FlowType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流程类型(FlowType)业务逻辑实现类
 *
 * @author sei
 * @since 2020-12-16 12:49:06
 */
@Service("flowTypeService")
public class FlowTypeService extends BaseEntityService<FlowType> {
    @Autowired
    private FlowTypeDao dao;

    @Override
    protected BaseEntityDao<FlowType> getDao() {
        return dao;
    }

}