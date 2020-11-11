package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.manager.entity.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限表(SecPermission)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:28
 */
@Repository
public interface PermissionDao extends BaseEntityDao<Permission> {

    /**
     * 根据角色列表查询权限列表
     *
     * @param ids 角色id列表
     * @return 权限列表
     */
    @Query(value = "SELECT DISTINCT sec_permission.* FROM sec_permission,sec_role,sec_role_permission WHERE sec_role.id = sec_role_permission.role_id AND sec_permission.id = sec_role_permission.permission_id AND sec_role.id IN (:ids)", nativeQuery = true)
    List<Permission> selectByRoleIdList(@Param("ids") List<String> ids);
}