package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.manager.dao.RoleDao;
import com.changhong.sei.manager.dao.UserGroupDao;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.entity.UserGroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
    private UserService userService;
    @Autowired
    private UserGroupService userGroupService;

    @Override
    protected BaseEntityDao<UserGroup> getDao() {
        return dao;
    }

    /**
     * 数据保存操作
     */
    @Override
    public OperateResultWithData<UserGroup> save(UserGroup entity) {
        long currentTimeMillis = System.currentTimeMillis();
        if (StringUtils.isBlank(entity.getId())) {
            entity.setCreateTime(currentTimeMillis);
        }
        entity.setUpdateTime(currentTimeMillis);
        return super.save(entity);
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        List<UserGroup> list = userGroupService.getParentsFromChildId(s);
        if (list != null && list.size() > 0) {
            // 功能角色存在已经分配的功能项，禁止删除！
            return OperateResult.operationFailure("功能角色存在已经分配的功能项，禁止删除！");
        }
        return super.preDelete(s);
    }

    /**
     * 根据功能角色的id获取已分配的用户
     *
     * @param groupId 用户组的id
     * @return 员工用户清单
     */
    public List<UserGroup> getParentsFromChildId(String groupId) {
        return userGroupService.getParentsFromChildId(groupId);
    }

    /**
     * 根据用户id 查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    public List<User> getChildrenFromParentId(String userId) {
        return userGroupService.getChildrenFromParentId(userId);
    }

}
