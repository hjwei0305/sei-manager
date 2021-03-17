package com.changhong.sei.ge.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.dao.NodeDao;
import com.changhong.sei.ge.entity.Node;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    /**
     * 根据环境代码获取节点
     *
     * @param env 环境代码
     * @return 返回节点
     */
    public List<Node> getNode(String env) {
        if (StringUtils.isNotBlank(env)) {
            Search search = Search.createSearch();
            search.addFilter(new SearchFilter(Node.FROZEN, Boolean.FALSE));
            search.addFilter(new SearchFilter(Node.FIELD_ENV_CODE, env));
            return this.findByFilters(search);
        } else {
            return Lists.newArrayList();
        }
    }
}