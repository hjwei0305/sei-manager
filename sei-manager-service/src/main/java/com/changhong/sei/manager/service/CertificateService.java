package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.dao.CertificateDao;
import com.changhong.sei.manager.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 凭证(Certificate)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Service("certificateService")
public class CertificateService extends BaseEntityService<Certificate> {
    @Autowired
    private CertificateDao dao;

    @Override
    protected BaseEntityDao<Certificate> getDao() {
        return dao;
    }

}