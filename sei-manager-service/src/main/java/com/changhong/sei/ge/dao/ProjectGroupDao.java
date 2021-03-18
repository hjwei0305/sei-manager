package com.changhong.sei.ge.dao;

import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.ge.entity.ProjectGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目组(ProjectGroup)数据库访问类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Repository
public interface ProjectGroupDao extends BaseTreeDao<ProjectGroup> {

    /**
     * 用名称模糊查询
     *
     * @param name 查询关键字
     * @return 查询结果集
     */
    List<ProjectGroup> findByNameLike(String name);
}