package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.BuildJob;
import org.springframework.stereotype.Repository;

/**
 * 构建任务(BuildJob)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface BuildJobDao extends BaseEntityDao<BuildJob> {

}