package com.changhong.sei.cicd.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.cicd.entity.BuildDetail;
import org.springframework.stereotype.Repository;

/**
 * 构建明细(BuildDetail)数据库访问类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Repository
public interface BuildDetailDao extends BaseEntityDao<BuildDetail> {

}