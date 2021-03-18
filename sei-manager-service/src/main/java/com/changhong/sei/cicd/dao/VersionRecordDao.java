package com.changhong.sei.cicd.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.cicd.entity.VersionRecord;
import org.springframework.stereotype.Repository;

/**
 * 版本发布记录(VersionRecord)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface VersionRecordDao extends BaseEntityDao<VersionRecord> {

}