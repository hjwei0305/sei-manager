package com.changhong.sei.config.dao;

import com.changhong.sei.config.entity.EnvVariableValue;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * 环境变量(ConfEnvVariableValue)数据库访问类
 *
 * @author sei
 * @since 2021-03-02 14:26:23
 */
@Repository
public interface EnvVariableValueDao extends BaseEntityDao<EnvVariableValue> {

}