package com.changhong.sei.datamodel.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.dao.ElementLabelDao;
import com.changhong.sei.datamodel.entity.ElementLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 元素标签关系(ElementLabel)业务逻辑实现类
 *
 * @author sei
 * @since 2020-07-28 17:31:46
 */
@Service("elementLabelService")
public class ElementLabelService extends BaseEntityService<ElementLabel> {
    @Autowired
    private ElementLabelDao dao;

    @Override
    protected BaseEntityDao<ElementLabel> getDao() {
        return dao;
    }

}