package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.ge.entity.Application;
import com.changhong.sei.ge.service.ApplicationService;
import com.changhong.sei.integrated.service.GitlabService;
import com.changhong.sei.manager.dao.UserGroupDao;
import com.changhong.sei.manager.dto.UserGroupDto;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.entity.UserGroup;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 角色业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@Service("userGroupService")
public class UserGroupService extends BaseEntityService<UserGroup> {
    @Autowired
    private UserGroupDao dao;
    @Autowired
    private UserGroupUserService userGroupUserService;

    @Override
    protected BaseEntityDao<UserGroup> getDao() {
        return dao;
    }

    /**
     * 根据功能角色的id获取已分配的用户
     *
     * @param groupId 用户组的id
     * @return 员工用户清单
     */
    public List<UserGroup> getParentsFromChildId(String groupId) {
        return userGroupUserService.getParentsFromChildId(groupId);
    }

    /**
     * 根据用户id 查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    public List<User> getChildrenFromParentId(String userId) {
        return userGroupUserService.getChildrenFromParentId(userId);
    }
}
