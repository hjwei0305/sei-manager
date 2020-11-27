package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.ApprovalRecord;
import org.springframework.stereotype.Repository;

/**
 * 审核记录(ApprovalRecord)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface ApprovalRecordDao extends BaseEntityDao<ApprovalRecord> {

}