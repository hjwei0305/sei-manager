# 数据类型初始化数据
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'IdOrKey', 'ID标识号', 36, NULL, 'String', 'VARCHAR', 'VARCHAR', 'VARCHAR2', 'VARCHAR', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'Name', '名称', 128, NULL, 'String', 'VARCHAR', 'VARCHAR', 'VARCHAR2', 'VARCHAR', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'Remark', '备注说明', 512, NULL, 'String', 'VARCHAR', 'VARCHAR', 'VARCHAR2', 'VARCHAR', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'Char', '单字符', 1, NULL, 'String', 'CHAR', 'CHAR', 'CHAR', 'CHAR', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'ShortString', '字串-短', 56, NULL, 'String', 'VARCHAR', 'VARCHAR', 'NVARCHAR2', 'NVARCHAR', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'MiddleString', '字串-中', 256, NULL, 'String', 'VARCHAR', 'VARCHAR', 'NVARCHAR2', 'NVARCHAR', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'LongString', '字串-长', 1024, NULL, 'String', 'VARCHAR', 'VARCHAR', 'NVARCHAR2', 'NVARCHAR', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'LongText', '大文本', NULL, NULL, 'String', 'LONGTEXT', 'TEXT', 'CLOB', 'NTEXT', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'Double', '小数', 32, 10, 'Double', 'DECIMAL', 'DECIMAL', 'NUMBER', 'DECIMAL', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'Ratio', '比例', 4, 2, 'Double', 'DECIMAL', 'DECIMAL', 'NUMBER', 'DECIMAL', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'Integer', '整数', NULL, NULL, 'Integer', 'INT', 'INT', 'INT', 'INT', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'BigInt', '大整数', NULL, NULL, 'Long', 'BIGINT', 'BIGINT', 'NUMBER', 'BIGINT', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'Money', '金额', 32, 8, 'Double', 'DECIMAL', 'DECIMAL', 'NUMBER', 'DECIMAL', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'YesNo', '是否', 1, NULL, 'Boolean', 'CHAR', 'CHAR', 'CHAR', 'CHAR', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'Date', '日期', NULL, NULL, 'Date', 'DATE', 'DATE', 'DATE', 'DATE', NULL, '1', '0');
INSERT INTO `mdms`.`data_type`(`id`, `code`, `name`, `data_length`, `decimal_places`, `java_type`, `mysql_type`,
                               `postgre_type`, `oracle_type`, `mssql_type`, `remark`, `system_`, `frozen`)
VALUES (UUID(), 'DateTime', '日期时间', NULL, NULL, 'Date', 'DATETIME', 'DATE', 'DATE', 'DATE', NULL, '1', '0');