package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.manager.dao.RoleDao;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * 角色业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@Service("roleService")
public class RoleService extends BaseEntityService<Role> {
    @Autowired
    private RoleDao dao;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleFeatureService roleFeatureService;
    @Autowired
    private UserService userService;

    @Override
    protected BaseEntityDao<Role> getDao() {
        return dao;
    }

    /**
     * 根据用户id 查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    public List<Role> selectByUserId(String userId) {
        return dao.selectByUserId(userId);
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        if (roleFeatureService.isExistByParent(s)) {
            //功能角色存在已经分配的功能项，禁止删除！
            return OperateResult.operationFailure("功能角色存在已经分配的功能项，禁止删除！");
        }
        List<User> list = userRoleService.getParentsFromChildId(s);
        if (list != null && list.size() > 0) {
            // 功能角色存在已经分配的功能项，禁止删除！
            return OperateResult.operationFailure("功能角色存在已经分配的功能项，禁止删除！");
        }
        return super.preDelete(s);
    }

    /**
     * 根据功能角色的id获取已分配的用户
     *
     * @param featureRoleId 功能角色的id
     * @return 员工用户清单
     */
    public List<User> getAssignedEmployeesByFeatureRole(String featureRoleId) {
        return userRoleService.getParentsFromChildId(featureRoleId);
    }

}
