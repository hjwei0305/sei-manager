package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.AppModuleDao;
import com.changhong.sei.deploy.dao.RequisitionRecordDao;
import com.changhong.sei.deploy.entity.AppModule;
import com.changhong.sei.deploy.entity.RequisitionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 申请记录(RequisitionRecord)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Service("requisitionRecordService")
public class RequisitionRecordService extends BaseEntityService<RequisitionRecord> {
    @Autowired
    private RequisitionRecordDao dao;

    @Override
    protected BaseEntityDao<RequisitionRecord> getDao() {
        return dao;
    }

}