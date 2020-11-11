package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.manager.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单表(Menu)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:32
 */
@Repository
public interface MenuDao extends BaseTreeDao<Menu> {

    /**
     * 用名称模糊查询
     *
     * @param name 查询关键字
     * @return 查询结果集
     */
    List<Menu> findByNameLike(String name);

    /**
     * 通过功能项查询
     *
     * @param featureId 查询关键字
     * @return 查询结果集
     */
    List<Menu> findByFeatureId(String featureId);

    /**
     * 查询其他菜单配置的功能项
     *
     * @param featureId 查询关键字
     * @return 查询结果集
     */
    Menu findFirstByFeatureIdAndIdNot(String featureId, String id);

}