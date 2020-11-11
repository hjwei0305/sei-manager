package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.manager.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色表(SecRole)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:30
 */
@Repository
public interface RoleDao extends BaseEntityDao<Role> {

    /**
     * 根据用户id 查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    @Query(value = "SELECT sec_role.* FROM sec_role,sec_user,sec_user_role WHERE sec_user.id = sec_user_role.user_id AND sec_role.id = sec_user_role.role_id AND sec_user.id = :userId", nativeQuery = true)
    List<Role> selectByUserId(@Param("userId") String userId);
}