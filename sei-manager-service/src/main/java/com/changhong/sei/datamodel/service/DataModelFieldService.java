package com.changhong.sei.datamodel.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.dao.DataModelFieldDao;
import com.changhong.sei.datamodel.entity.DataModelField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 模型字段(DataModelField)业务逻辑实现类
 *
 * @author sei
 * @since 2020-07-28 17:31:42
 */
@Service("dataModelFieldService")
public class DataModelFieldService extends BaseEntityService<DataModelField> {
    @Autowired
    private DataModelFieldDao dao;

    @Override
    protected BaseEntityDao<DataModelField> getDao() {
        return dao;
    }

    /**
     * 按数据模型id查询模型字段
     *
     * @param dataModelId 数据模型id
     * @return 返回指定模型id的字段清单
     */
    public List<DataModelField> findByDataModelId(String dataModelId) {
        return dao.findByDataModelIdOrderByRank(dataModelId);
    }

    /**
     * 按数据模型id删除模型字段
     *
     * @param dataModelId 数据模型id
     * @return 返回删除的记录数
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteByDataModelId(String dataModelId) {
        return dao.deleteByDataModelId(dataModelId);
    }
}