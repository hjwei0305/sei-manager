package com.changhong.sei.config.dao;

import com.changhong.sei.config.entity.AuthWhitelist;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * 网关认证白名单(AuthWhitelist)数据库访问类
 *
 * @author sei
 * @since 2021-02-22 21:43:46
 */
@Repository
public interface AuthWhitelistDao extends BaseEntityDao<AuthWhitelist> {

}