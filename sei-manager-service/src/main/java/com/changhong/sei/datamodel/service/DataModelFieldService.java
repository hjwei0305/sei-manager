package com.changhong.sei.datamodel.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.dao.DataModelFieldDao;
import com.changhong.sei.datamodel.entity.DataModelField;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
        if (StringUtils.isBlank(dataModelId)) {
            return new ArrayList<>();
        }
        return dao.findListByProperty(DataModelField.FIELD_DATA_MODEL_ID, dataModelId);
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

    /**
     * 检查字段是否存在已发布的
     *
     * @param dataModelId 数据模型id
     * @return 存在返回true, 反之false
     */
    public boolean checkExistPublished(String dataModelId) {
        List<DataModelField> fieldList = findByDataModelId(dataModelId);
        if (CollectionUtils.isEmpty(fieldList)) {
            return false;
        }
        return fieldList.stream().anyMatch(DataModelField::getPublished);
    }

    /**
     * 获取未发布的字段清单
     *
     * @param dataModelId 数据模型id
     * @return 未发布的字段清单
     */
    public List<DataModelField> getNonPublish(String dataModelId) {
        List<DataModelField> fieldList = findByDataModelId(dataModelId);
        if (CollectionUtils.isEmpty(fieldList)) {
            return fieldList;
        }
        return fieldList.stream().filter(f -> !f.getPublished()).collect(Collectors.toList());
    }
}