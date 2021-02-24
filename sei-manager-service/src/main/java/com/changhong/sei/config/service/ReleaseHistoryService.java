package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.ReleaseHistoryDao;
import com.changhong.sei.config.entity.ReleaseHistory;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 发布历史(ConfReleaseHistory)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:44:11
 */
@Service("releaseHistoryService")
public class ReleaseHistoryService extends BaseEntityService<ReleaseHistory> {
    @Autowired
    private ReleaseHistoryDao dao;

    @Override
    protected BaseEntityDao<ReleaseHistory> getDao() {
        return dao;
    }

}