package com.changhong.sei.datamodel.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.datamodel.entity.DataModelField;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模型字段(DataModelField)数据库访问类
 *
 * @author sei
 * @since 2020-07-28 17:31:42
 */
@Repository
public interface DataModelFieldDao extends BaseEntityDao<DataModelField> {

    /**
     * 按数据模型id查询模型字段
     *
     * @param dataModelId 数据模型id
     * @return 返回指定模型id的字段清单
     */
    List<DataModelField> findByDataModelIdOrderByRank(String dataModelId);

    /**
     * 按数据模型id删除模型字段
     *
     * @param dataModelId 数据模型id
     * @return 返回删除的记录数
     */
    @Modifying
    @Query("delete from DataModelField f where f.dataModelId = :dataModelId")
    int deleteByDataModelId(@Param("dataModelId") String dataModelId);
}