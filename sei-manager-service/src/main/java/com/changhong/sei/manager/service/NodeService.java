package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.dao.NodeDao;
import com.changhong.sei.manager.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 服务器节点(Node)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("nodeService")
public class NodeService extends BaseEntityService<Node> {
    @Autowired
    private NodeDao dao;

    @Override
    protected BaseEntityDao<Node> getDao() {
        return dao;
    }

}