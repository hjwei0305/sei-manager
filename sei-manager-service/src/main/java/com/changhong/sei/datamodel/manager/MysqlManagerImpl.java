package com.changhong.sei.datamodel.manager;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.datamodel.dto.DBType;
import com.changhong.sei.datamodel.entity.DataModel;
import com.changhong.sei.datamodel.entity.DataModelField;
import com.changhong.sei.datamodel.entity.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
@Component("MYSQL")
public class MysqlManagerImpl implements DatabaseManager {
    private static final Logger LOG = LoggerFactory.getLogger(MysqlManagerImpl.class);

    /**
     * 生产数据库脚本
     *
     * @param dataSource 数据源信息
     * @param fields     字段
     */
    @Override
    public ResultData<String> generateScript(DataSource dataSource, DataModel dataModel, List<DataModelField> fields) {
        Connection conn = getConnection(dataSource);

//        DBType.MYSQL
        return null;
    }

    private List<String> generateScript(DataModel dataModel, List<DataModelField> fields) {
        String table = "CREATE TABLE student " +
                "(id INTEGER not NULL, " +
                " first VARCHAR(255), " +
                " last VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(dataModel.getTableName()).append(" ( ");

        String primaryKey = "";
        for (DataModelField field : fields) {
            if (field.getPrimaryKey()) {
                primaryKey = field.getFieldName();
            }
            sql.append("");
        }
        // 主键
        sql.append(" PRIMARY KEY ( ").append(primaryKey).append(" ))");

        return null;
    }

    /**
     * 获取数据库连接
     */
    private Connection getConnection(DataSource dataSource) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), new String(dataSource.getPassword()));
            LOG.debug("数据库连接成功");
        } catch (SQLException | ClassNotFoundException e) {
            LOG.error("获取数据库连接异常.", e);
        }
        return conn;
    }
}
