package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.ChangeSetDao;
import com.changhong.sei.config.entity.ChangeSet;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 配置变更集(ConfChangeset)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:43:58
 */
@Service("changeSetService")
public class ChangeSetService extends BaseEntityService<ChangeSet> {
    @Autowired
    private ChangeSetDao dao;

    @Override
    protected BaseEntityDao<ChangeSet> getDao() {
        return dao;
    }

}