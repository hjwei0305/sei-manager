package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.AuthWhitelistDao;
import com.changhong.sei.config.entity.AuthWhitelist;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 网关认证白名单(AuthWhitelist)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:43:58
 */
@Service("authWhitelistService")
public class AuthWhitelistService extends BaseEntityService<AuthWhitelist> {
    @Autowired
    private AuthWhitelistDao dao;

    @Override
    protected BaseEntityDao<AuthWhitelist> getDao() {
        return dao;
    }

}