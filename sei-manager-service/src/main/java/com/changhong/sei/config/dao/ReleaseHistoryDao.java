package com.changhong.sei.config.dao;

import com.changhong.sei.config.entity.ReleaseHistory;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * 发布历史(ConfReleaseHistory)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:44:11
 */
@Repository
public interface ReleaseHistoryDao extends BaseEntityDao<ReleaseHistory> {

}