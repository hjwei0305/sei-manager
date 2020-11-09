package com.changhong.sei.datamodel.manager;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.datamodel.entity.DataModel;
import com.changhong.sei.datamodel.entity.DataModelField;
import com.changhong.sei.datamodel.entity.DataSource;

import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-08-18 11:03
 */
public interface DatabaseManager {

    /**
     * 生产数据库脚本
     * @param dataSource 数据源信息
     * @param fields 字段
     * @return
     */
    ResultData<String> generateScript(DataSource dataSource, DataModel dataModel, List<DataModelField> fields);
}
