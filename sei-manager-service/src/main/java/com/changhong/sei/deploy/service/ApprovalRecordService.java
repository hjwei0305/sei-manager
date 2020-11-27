package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.ApprovalRecordDao;
import com.changhong.sei.deploy.entity.ApprovalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 审核记录(ApprovalRecord)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Service("approvalRecordService")
public class ApprovalRecordService extends BaseEntityService<ApprovalRecord> {
    @Autowired
    private ApprovalRecordDao dao;

    @Override
    protected BaseEntityDao<ApprovalRecord> getDao() {
        return dao;
    }

}