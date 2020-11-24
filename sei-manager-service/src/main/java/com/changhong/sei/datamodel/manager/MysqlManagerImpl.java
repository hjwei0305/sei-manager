package com.changhong.sei.datamodel.manager;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.datamodel.entity.DataModel;
import com.changhong.sei.datamodel.entity.DataModelField;
import com.changhong.sei.datamodel.entity.DataSource;
import com.changhong.sei.datamodel.service.DataModelFieldService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-08-18 11:04
 */
@Component("MYSQL")
public class MysqlManagerImpl implements DatabaseManager {
    private static final Logger LOG = LoggerFactory.getLogger(MysqlManagerImpl.class);

    @Autowired
    private DataModelFieldService fieldService;

    /**
     * 生产数据库脚本
     *
     * @param dataSource 数据源信息
     * @param dataModel  数据模型
     * @param fields     字段
     * @return 返回sql脚本
     */
    @Override
    public ResultData<String> generateScript(DataSource dataSource, DataModel dataModel, List<DataModelField> fields) {
        String dataModelId = dataModel.getId();
        // 按模型id获取字段
        List<DataModelField> fieldList = fieldService.findByDataModelId(dataModelId);
        if (CollectionUtils.isEmpty(fieldList)) {
            return ResultData.fail("模型字段不存在.");
        }

        StringBuffer script = new StringBuffer();
        // 检查字段是否存在已发布的
        boolean checkExistPublished = fieldList.stream().anyMatch(DataModelField::getPublished);
        if (checkExistPublished) {
            // 存在则增加字段
            createTable(script, dataModel, fieldList);
        } else {
            // 不存在则创建表
            addColumn(script, dataModel.getTableName(), fieldList);
        }
        return ResultData.success(script.toString());
    }

    /**
     * 执行生成的数据库脚本
     *
     * @param dataSource 数据源信息
     * @param dataModel  数据模型
     * @param fields     字段
     * @return 执行脚本
     */
    @Override
    public ResultData<String> executeGenerateScript(DataSource dataSource, DataModel dataModel, List<DataModelField> fields) {
        ResultData<String> resultData = generateScript(dataSource, dataModel, fields);

        if (resultData.successful()) {
            String script = resultData.getData();
            LOG.debug("执行脚本: {}", script);
            PreparedStatement statement = null;
            // 获取数据库连接
            try (Connection conn = getConnection(dataSource)) {
                // 执行脚本
                statement = conn.prepareStatement(script);
                statement.executeUpdate();

                // todo 记录脚本

                return ResultData.success();
            } catch (SQLException e) {
                LOG.error("", e);
                return ResultData.fail("");
            } finally {
                if (Objects.nonNull(statement)) {
                    try {
                        statement.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        } else {
            return resultData;
        }
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

    /**
     * 创建表
     *
     * @param script 脚本字符串
     * @param model  数据模型
     * @param fields 字段集合
     */
    private void createTable(StringBuffer script, DataModel model, List<DataModelField> fields) {
        script.append("CREATE TABLE `").append(model.getTableName()).append("` ( \n\r");
        // 添加字段
        addColumn(script, model.getTableName(), fields);

        // 获取主键
        String primaryKey = fields.stream()
                // 过滤主键字段
                .filter(DataModelField::getPrimaryKey)
                .map(DataModelField::getFieldName).findFirst().orElse("");
        if (StringUtils.isNotBlank(primaryKey)) {
            script.append("PRIMARY KEY (`").append(primaryKey).append("`) USING BTREE \n\r");
        } else {
            // 移除最后一个逗号

        }
        script.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='").append(model.getRemark()).append("'; \n\r");
    }

    /**
     * 添加字段
     *
     * @param script 脚本字符串
     * @param table  数据表名
     * @param fields 字段集合
     */
    private void addColumn(StringBuffer script, String table, List<DataModelField> fields) {
        // ALTER TABLE `user_profile`  ADD COLUMN `preferences` mediumtext NULL COMMENT '用户偏好设置' AFTER `portal_id`;
        for (DataModelField field : fields) {

            script.append(" \n\r");
        }
    }
}
