package com.changhong.sei.datamodel.manager;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.datamodel.entity.DataModel;
import com.changhong.sei.datamodel.entity.DataModelField;
import com.changhong.sei.datamodel.entity.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-08-18 11:04
 */
public class MysqlManagerImpl implements DatabaseManager {

    /**
     * 生产数据库脚本
     *
     * @param dataSource 数据源信息
     * @param fields     字段
     */
    @Override
    public ResultData<String> generateScript(DataSource dataSource, DataModel dataModel, List<DataModelField> fields) {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), new String(dataSource.getPassword()));
            System.out.println("数据库连接成功");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
