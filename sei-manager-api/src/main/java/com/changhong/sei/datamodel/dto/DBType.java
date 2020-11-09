package com.changhong.sei.datamodel.dto;

import com.changhong.sei.annotation.Remark;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 实现功能： 数据库类型枚举
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-07-30 08:50
 */
public enum DBType implements Serializable {

    @Remark("MySQL")
    MYSQL(3306, "jdbc:mysql://{host}:{port}/{database}"),
    @Remark("PostgreSql")
    POSTGRESQL(5432, "jdbc:postgresql://{host}:{port}/{database}"),
    @Remark("Oracle")
    ORACLE(1521,"jdbc:oracle:thin:@{host}:{port}:{database}"),
    @Remark("SQL SERVER")
    SQLSERVER(1433, "jdbc:sqlserver://{host}:{port};database={database}")
    ;

    private int port = -1;
    private String urlTemp;
    DBType(int port, String urlTemp) {
        this.port = port;
        this.urlTemp = urlTemp;
    }

    public int getPort() {
        return port;
    }

    public String getUrlTemp() {
        return urlTemp;
    }

    public static String getUrlTemp(String dbType) {
        for (DBType type : DBType.values()) {
            if (StringUtils.equalsIgnoreCase(type.name(), dbType)) {
                return type.getUrlTemp();
            }
        }
        return StringUtils.EMPTY;
    }
}
