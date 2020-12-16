package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowTypeNodeDao;
import com.changhong.sei.deploy.entity.FlowTypeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 流程类型节点定义(FlowTypeNode)业务逻辑实现类
 *
 * @author sei
 * @since 2020-12-16 12:49:08
 */
@Service("flowTypeNodeService")
public class FlowTypeNodeService extends BaseEntityService<FlowTypeNode> {
    @Autowired
    private FlowTypeNodeDao dao;

    @Override
    protected BaseEntityDao<FlowTypeNode> getDao() {
        return dao;
    }

}