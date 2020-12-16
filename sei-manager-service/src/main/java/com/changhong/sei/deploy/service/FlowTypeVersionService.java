package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.FlowTypeVersionDao;
import com.changhong.sei.deploy.entity.FlowTypeVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    /**
     * 通过流程类型获取版本清单
     *
     * @param typeId 流程类型id
     * @return 返回结果
     */
    public List<FlowTypeVersion> getTypeVersionByTypeId(String typeId) {
        return dao.findListByProperty(FlowTypeVersion.FIELD_TYPE_ID, typeId);
    }
}