package com.changhong.sei.cicd.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.cicd.entity.RequisitionOrder;
import org.springframework.stereotype.Repository;

/**
 * 申请记录(RequisitionOrder)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface RequisitionOrderDao extends BaseEntityDao<RequisitionOrder> {

}