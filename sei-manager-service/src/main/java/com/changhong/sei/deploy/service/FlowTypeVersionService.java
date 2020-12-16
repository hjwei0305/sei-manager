package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowTypeVersionDao;
import com.changhong.sei.deploy.entity.FlowTypeVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 流程类型版本(FlowTypeVersion)业务逻辑实现类
 *
 * @author sei
 * @since 2020-12-16 12:49:11
 */
@Service("flowTypeVersionService")
public class FlowTypeVersionService extends BaseEntityService<FlowTypeVersion> {
    @Autowired
    private FlowTypeVersionDao dao;

    @Override
    protected BaseEntityDao<FlowTypeVersion> getDao() {
        return dao;
    }

}