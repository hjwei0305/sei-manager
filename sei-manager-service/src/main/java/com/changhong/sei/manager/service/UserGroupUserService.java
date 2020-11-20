package com.changhong.sei.manager.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.manager.dao.UserGroupUserDao;
import com.changhong.sei.manager.dao.UserRoleDao;
import com.changhong.sei.manager.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 实现功能：用户分配的功能角色的业务逻辑实现
 */
@Service
public class UserGroupUserService extends BaseRelationService<UserGroupUser, UserGroup, User> {
    @Autowired
    private UserGroupUserDao dao;

    @Autowired
    private UserService userService;

    @Override
    protected BaseRelationDao<UserGroupUser, UserGroup, User> getDao() {
        return dao;
    }

    /**
     * 获取可以分配的子实体清单
     *
     * @return 子实体清单
     */
    @Override
    protected List<User> getCanAssignedChildren(String parentId) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(User.FIELD_ADMIN, Boolean.FALSE));
        search.addFilter(new SearchFilter(User.FIELD_STATUS, Boolean.TRUE));
        return userService.findByFilters(search);
    }

    /**
     * 创建分配关系
     *
     * @param parentId 父实体Id
     * @param childIds 子实体Id清单
     * @return 操作结果
     */
    @Override
    public OperateResult insertRelations(String parentId, List<String> childIds) {
        if (parentId.equals(ContextUtil.getUserId())) {
            //00031 = 不能为当前用户分配功能角色！
            return OperateResult.operationFailure("不能为当前用户分配功能角色！");
        }
        OperateResult result = super.insertRelations(parentId, childIds);
        // 清除用户权限缓存
//        AuthorityUtil.cleanUserAuthorizedCaches(parentId);
        return result;
    }

    /**
     * 移除分配关系
     *
     * @param parentId 父实体Id
     * @param childIds 子实体Id清单
     * @return 操作结果
     */
    @Override
    public OperateResult removeRelations(String parentId, List<String> childIds) {
        if (parentId.equals(ContextUtil.getUserId())) {
            //00032 = 不能移除当前用户的功能角色！
            return OperateResult.operationFailure("不能移除当前用户的功能角色！");
        }
        OperateResult result = super.removeRelations(parentId, childIds);
        // 清除用户权限缓存
//        AuthorityUtil.cleanUserAuthorizedCaches(parentId);
        return result;
    }

    /**
     * 通过父实体Id获取子实体清单
     *
     * @param parentId 父实体Id
     * @return 子实体清单
     */
    @Override
    public List<User> getChildrenFromParentId(String parentId) {
        // 获取分配关系
        List<UserGroupUser> groupUsers = getRelationsByParentId(parentId);
        List<User> users = new LinkedList<>();
        groupUsers.forEach(r -> {
            User user = r.getChild();
            users.add(user);
        });
        return users;
    }

    /**
     * 通过子实体Id获取父实体清单
     *
     * @param childId 子实体Id
     * @return 父实体清单
     */
    @Override
    public List<UserGroup> getParentsFromChildId(String childId) {
        List<UserGroup> users = super.getParentsFromChildId(childId);
        if (CollectionUtils.isEmpty(users)) {
            return new ArrayList<>();
        }
        return users;
    }
}
