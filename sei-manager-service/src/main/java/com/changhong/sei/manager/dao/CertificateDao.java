package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.manager.entity.Certificate;
import org.springframework.stereotype.Repository;

/**
 * 凭证(Certificate)数据库访问类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Repository
public interface CertificateDao extends BaseEntityDao<Certificate> {

}