package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.dao.PermissionDao;
import com.changhong.sei.manager.dao.RoleDao;
import com.changhong.sei.manager.dao.UserDao;
import com.changhong.sei.manager.entity.Permission;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.vo.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户表(SecUser)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@Service("serService")
public class UserService extends BaseEntityService<User> implements UserDetailsService {
    @Autowired
    private UserDao dao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    protected BaseEntityDao<User> getDao() {
        return dao;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        User user = dao.findByUsernameOrEmailOrPhone(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone));
        List<Role> roles = roleDao.selectByUserId(user.getId());
        List<String> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        List<Permission> permissions = permissionDao.selectByRoleIdList(roleIds);
        return UserPrincipal.create(user, roles, permissions);
    }

//    /**
//     * 在线用户分页列表
//     *
//     * @param pageCondition 分页参数
//     * @return 在线用户分页列表
//     */
//    public PageResult<OnlineUser> onlineUser(PageCondition pageCondition) {
//        PageResult<String> keys = redisUtil.findKeysForPage(Consts.REDIS_JWT_KEY_PREFIX + Consts.SYMBOL_STAR, pageCondition.getCurrentPage(), pageCondition.getPageSize());
//        List<String> rows = keys.getRows();
//        Long total = keys.getTotal();
//
//        // 根据 redis 中键获取用户名列表
//        List<String> usernameList = rows.stream()
//                .map(s -> StrUtil.subAfter(s, Consts.REDIS_JWT_KEY_PREFIX, true))
//                .collect(Collectors.toList());
//        // 根据用户名查询用户信息
//        List<User> userList = userDao.findByUsernameIn(usernameList);
//
//        // 封装在线用户信息
//        List<OnlineUser> onlineUserList = Lists.newArrayList();
//        userList.forEach(user -> onlineUserList.add(OnlineUser.create(user)));
//
//        return new PageResult<>(onlineUserList, total);
//    }
//
//    /**
//     * 踢出在线用户
//     *
//     * @param names 用户名列表
//     */
//    public void kickout(List<String> names) {
//        // 清除 Redis 中的 JWT 信息
//        List<String> redisKeys = names.parallelStream()
//                .map(s -> Consts.REDIS_JWT_KEY_PREFIX + s)
//                .collect(Collectors.toList());
//        redisUtil.delete(redisKeys);
//
//        // 获取当前用户名
//        String currentUsername = SecurityUtil.getCurrentUsername();
//        names.parallelStream()
//                .forEach(name -> {
//                    // TODO: 通知被踢出的用户已被当前登录用户踢出，
//                    //  后期考虑使用 websocket 实现，具体伪代码实现如下。
//                    //  String message = "您已被用户【" + currentUsername + "】手动下线！";
//                    log.debug("用户【{}】被用户【{}】手动下线！", name, currentUsername);
//                });
//    }
}