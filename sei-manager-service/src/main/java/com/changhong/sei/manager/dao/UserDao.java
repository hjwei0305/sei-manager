package com.changhong.sei.manager.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.manager.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户表(SecUser)数据库访问类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@Repository
public interface UserDao extends BaseEntityDao<User> {

    /**
     * 根据用户名、邮箱、手机号查询用户
     *
     * @param username 用户名
     * @param email    邮箱
     * @param phone    手机号
     * @return 用户信息
     */
    Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);

    /**
     * 根据用户名列表查询用户列表
     *
     * @param usernameList 用户名列表
     * @return 用户列表
     */
    List<User> findByUsernameIn(List<String> usernameList);
}