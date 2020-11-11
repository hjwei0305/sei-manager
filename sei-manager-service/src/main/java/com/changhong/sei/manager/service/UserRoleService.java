package com.changhong.sei.manager.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.manager.dao.UserRoleDao;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.entity.UserRole;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 实现功能：用户分配的功能角色的业务逻辑实现
 */
@Service
public class UserRoleService extends BaseRelationService<UserRole, User, Role> {
    @Autowired
    private UserRoleDao dao;

    @Autowired
    private RoleService featureRoleService;

    @Override
    protected BaseRelationDao<UserRole, User, Role> getDao() {
        return dao;
    }

    /**
     * 获取可以分配的子实体清单
     *
     * @return 子实体清单
     */
    @Override
    protected List<Role> getCanAssignedChildren(String parentId) {
        // 判断用户权限
        return featureRoleService.getCanAssignedRoles();
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
            return OperateResult.operationFailure("00031");
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
            return OperateResult.operationFailure("00032");
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
    public List<Role> getChildrenFromParentId(String parentId) {
        // 获取分配关系
        List<UserRole> userFeatureRoles = getRelationsByParentId(parentId);
        // 设置授权有效期
        List<Role> featureRoles = new LinkedList<>();
        userFeatureRoles.forEach(r -> {
            Role featureRole = r.getChild();
            featureRole.setRelationId(r.getId());
            featureRoles.add(featureRole);
        });
        return featureRoles;
    }

    /**
     * 获取当前有效的授权功能角色清单
     *
     * @param parentId 用户Id
     * @return 有效的授权功能角色清单
     */
    public List<Role> getEffectiveChildren(String parentId) {
        // 获取分配的功能项
        List<Role> roles = getChildrenFromParentId(parentId);
        if (CollectionUtils.isEmpty(roles)) {
            return new LinkedList<>();
        }
        return roles;
    }

    /**
     * 通过子实体Id获取父实体清单
     *
     * @param childId 子实体Id
     * @return 父实体清单
     */
    @Override
    public List<User> getParentsFromChildId(String childId) {
        List<User> users = super.getParentsFromChildId(childId);
        if (CollectionUtils.isEmpty(users)) {
            return new ArrayList<>();
        }
        return users;
    }
}
