package com.changhong.sei.cicd.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.cicd.entity.BuildJob;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 构建任务(BuildJob)数据库访问类
 *
 * @author sei
 * @since 2020-10-30 15:20:23
 */
@Repository
public interface BuildJobDao extends BaseEntityDao<BuildJob> {

    /**
     * 更新版本号
     *
     * @return 返回更新的记录数
     */
    @Modifying
    @Query("update BuildJob t set t.allowBuild = :allowBuild where t.jobName = :jobName and t.type = :type ")
    int updateAllowBuildStatus(@Param("jobName") String jobName, @Param("type") String type, @Param("allowBuild") Boolean allowBuild);

    /**
     * 根据环境代码和应用模块id获取部署的tag
     *
     * @param envCode  环境代码
     * @param moduleId 应用模块id
     * @return 发挥tagName
     */
    @Query("select max(t.refTag) from BuildJob t where t.envCode = :envCode and t.moduleId = :moduleId and t.buildStatus = 'SUCCESS' and t.type='DEPLOY' ")
    String getLastTag(@Param("envCode") String envCode, @Param("moduleId") String moduleId);
}