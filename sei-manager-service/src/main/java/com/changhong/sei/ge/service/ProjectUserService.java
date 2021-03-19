package com.changhong.sei.ge.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.dao.ProjectUserDao;
import com.changhong.sei.ge.entity.ProjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目用户(ProjectUser)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service
public class ProjectUserService extends BaseEntityService<ProjectUser> {
    @Autowired
    private ProjectUserDao dao;

    @Override
    protected BaseEntityDao<ProjectUser> getDao() {
        return dao;
    }

}