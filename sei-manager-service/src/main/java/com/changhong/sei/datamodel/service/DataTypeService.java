package com.changhong.sei.datamodel.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.dao.DataTypeDao;
import com.changhong.sei.datamodel.entity.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 数据类型(DataType)业务逻辑实现类
 *
 * @author sei
 * @since 2020-07-28 17:31:45
 */
@Service("dataTypeService")
public class DataTypeService extends BaseEntityService<DataType> {
    @Autowired
    private DataTypeDao dao;

    @Override
    protected BaseEntityDao<DataType> getDao() {
        return dao;
    }

}