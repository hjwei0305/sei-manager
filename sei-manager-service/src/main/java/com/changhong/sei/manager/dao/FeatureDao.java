package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.manager.entity.Feature;
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
public interface FeatureDao extends BaseEntityDao<Feature> {

    /**
     * 根据角色列表查询权限列表
     *
     * @param ids 角色id列表
     * @return 权限列表
     */
    @Query(value = "SELECT DISTINCT sec_feature.* FROM sec_feature,sec_role,sec_role_feature WHERE sec_role.id = sec_role_feature.role_id AND sec_feature.id = sec_role_feature.feature_id AND sec_role.id IN (:ids)", nativeQuery = true)
    List<Feature> selectByRoleIdList(@Param("ids") List<String> ids);
}