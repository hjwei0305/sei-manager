package com.changhong.sei.cicd.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.cicd.entity.ReleaseVersionRequisition;
import org.springframework.stereotype.Repository;

/**
 * 发版申请(ReleaseVersionRequisition)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface ReleaseVersionRequisitionDao extends BaseEntityDao<ReleaseVersionRequisition> {

}