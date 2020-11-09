package com.changhong.sei.datamodel.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.dao.DataSourceDao;
import com.changhong.sei.datamodel.entity.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 数据源(DataSource)业务逻辑实现类
 *
 * @author sei
 * @since 2020-07-28 17:31:44
 */
@Service("dataSourceService")
public class DataSourceService extends BaseEntityService<DataSource> {
    @Autowired
    private DataSourceDao dao;

    @Override
    protected BaseEntityDao<DataSource> getDao() {
        return dao;
    }

}