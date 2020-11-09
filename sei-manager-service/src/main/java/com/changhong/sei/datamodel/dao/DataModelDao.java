package com.changhong.sei.datamodel.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.datamodel.entity.DataModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据模型(DataModel)数据库访问类
 *
 * @author sei
 * @since 2020-07-28 17:31:39
 */
@Repository
public interface DataModelDao extends BaseEntityDao<DataModel> {

    /**
     * 根据模型类型代码查询数据模型
     *
     * @param modelTypeCode 模型类型代码
     * @return 返回指定类型的数据模型集合
     */
    List<DataModel> findByModelTypeCode(String modelTypeCode);
}