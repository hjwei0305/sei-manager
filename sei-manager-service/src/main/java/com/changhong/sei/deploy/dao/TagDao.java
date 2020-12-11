package com.changhong.sei.deploy.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.deploy.entity.Tag;
import org.springframework.stereotype.Repository;

/**
 * 应用标签(Tag)数据库访问类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Repository
public interface TagDao extends BaseEntityDao<Tag> {

}