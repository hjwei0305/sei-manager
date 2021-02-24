package com.changhong.sei.config.dao;

import com.changhong.sei.config.entity.ChangeSet;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * 配置变更集(ConfChangeset)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:43:58
 */
@Repository
public interface ChangeSetDao extends BaseEntityDao<ChangeSet> {

}