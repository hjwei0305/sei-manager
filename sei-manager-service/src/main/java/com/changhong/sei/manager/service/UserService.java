package com.changhong.sei.manager.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.manager.dao.UserDao;
import com.changhong.sei.manager.entity.Feature;
import com.changhong.sei.manager.entity.Menu;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.vo.UserPrincipal;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户表(SecUser)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@Service("userService")
public class UserService extends BaseEntityService<User> implements UserDetailsService {
    @Autowired
    private UserDao dao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleFeatureService roleFeatureService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected BaseEntityDao<User> getDao() {
        return dao;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        User user = dao.findByUsernameOrEmailOrPhone(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone));
        List<Role> roles = roleService.selectByUserId(user.getId());
        List<String> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        List<Feature> permissions = featureService.selectByRoleIdList(roleIds);
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

    public void updatePassword(String userId, String oldPassword, String password) {

    }

    /**
     * 更新密码
     *
     * @param userId   用户id
     * @param password md5散列值
     * @return 返回更新结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> updatePassword(String userId, String password) {
        User user = dao.findOne(userId);
        if (Objects.isNull(user)) {
            return ResultData.fail("用户不存在.");
        }

        user.setPassword(passwordEncoder.encode(password));
        this.save(user);
        return ResultData.success();
    }

    /**
     * 获取用户有权限的功能项清单
     *
     * @param userId 平台用户Id
     * @return 功能项清单
     */
    @Cacheable(value = "UserAuthorizedFeaturesCache", key = "'UserAuthorizedFeatures:'+#userId")
    public List<Feature> getUserAuthorizedFeatures(String userId) {
        List<Feature> result = new ArrayList<>();
        //获取用户
        User user = findOne(userId);
        if (user == null) {
            return result;
        }

        //一般用户的功能角色
        //获取用户授权的角色
        List<Role> authRoles = userRoleService.getEffectiveChildren(user.getId());
        Set<Role> userRoles = new HashSet<>(authRoles);
        //获取角色分配的功能项清单
        if (userRoles.isEmpty()) {
            return result;
        }
        List<String> userRoleIds = new ArrayList<>();
        userRoles.forEach((r) -> userRoleIds.add(r.getId()));
        List<Feature> features = roleFeatureService.getChildrenFromParentIds(userRoleIds);
        result.addAll(features);
        return result;
    }

    /**
     * 获取用户有权限的操作菜单项清单(启用缓存)
     *
     * @param userId 用户Id
     * @return 操作菜单树
     */
    @Cacheable(value = "UserAuthorizedMenusCache", key = "'UserAuthorizedMenus:'+#userId")
    public List<Menu> getUserAuthorizedMenus(String userId) {
        //获取用户
        User user = findOne(userId);
        if (user == null) {
            throw new ServiceException("租户【" + ContextUtil.getTenantCode() + "】,用户【" + userId + "】不存在！");
        }

        Set<Menu> userMenus = new HashSet<>();
        // 获取所有菜单
        List<Menu> allMenus = menuService.findAll();

        //判断租户管理员和一般用户
        //--获取用户有权限的功能项清单
        List<Feature> userFeatures = this.getUserAuthorizedFeatures(userId);
        //通过功能项清单获取菜单节点
        List<Menu> memuNodes = MenuService.getMenusByFeatures(userFeatures, allMenus);
        if (CollectionUtils.isNotEmpty(memuNodes)) {
            userMenus.addAll(memuNodes);
            //拼接菜单关联的父节点
            MenuService.generateUserMenuNodes(userMenus, memuNodes, allMenus);
        }

        // 设置功能项的url到菜单
        userMenus.forEach((m) -> {
            Feature feature = m.getFeature();
            if (Objects.nonNull(feature)) {
                m.setMenuUrl(feature.getUrl());
            }
        });
        return new ArrayList<>(userMenus);
    }

    /**
     * 获取用户有权限分配的功能项清单
     *
     * @param userId 用户Id
     * @return 可分配的功能项清单
     */
    @Cacheable(value = "UserCanAssignFeaturesCache", key = "'UserCanAssignFeatures:'+#userId")
    public List<Feature> getUserCanAssignFeatures(String userId) {
        List<Feature> result = new ArrayList<>();
        //获取用户
        User user = findOne(userId);
        if (user == null) {
            return result;
        }
        //一般用户的可分配功能角色
        List<Role> authRoles = userRoleService.getEffectiveChildren(user.getId());
        Set<Role> userRoles = new HashSet<>(authRoles);

        //获取角色分配的功能项清单
        if (userRoles.isEmpty()) {
            return result;
        }
        List<String> userRoleIds = new ArrayList<>();
        userRoles.forEach((r) -> userRoleIds.add(r.getId()));
        List<Feature> features = roleFeatureService.getChildrenFromParentIds(userRoleIds);
        result.addAll(features);
        return result;
    }

    /**
     * 获取用户前端权限检查的功能项键值
     *
     * @param userId 用户Id
     * @return 功能项键值
     */
    @Cacheable(value = "UserAuthorizedFeatureMapsCache", key = "'UserAuthorizedFeatureMaps:'+#userId")
    public Map<String, Set<String>> getUserAuthorizedFeatureMaps(String userId) {
        Map<String, Set<String>> result = new HashMap<>();
        // 获取用户有权限的功能项清单
        List<Feature> authFeatures = this.getUserAuthorizedFeatures(userId);
        if (CollectionUtils.isEmpty(authFeatures)) {
            return new LinkedHashMap<>();
        }
        //循环构造键值对
        for (Feature feature : authFeatures) {
            // 只添加操作功能项
            if (feature.getType() != 2) {
                continue;
            }
            //判断分组代码键值是否存在
            String key = feature.getId();
            Set<String> codes = result.get(key);
            if (Objects.isNull(codes)) {
                codes = new LinkedHashSet<>();
            }
            //添加功能项代码
            codes.add(feature.getPermission());
            result.put(key, codes);
        }
        return result;
    }

    /**
     * 获取用户前端权限检查的功能项键值
     *
     * @param userId 用户Id
     * @return 功能项键值
     */
    public Map<String, Map<String, String>> getUserAuthorizedFeatureAppMaps(String userId) {
        Map<String, Map<String, String>> result = new HashMap<>();
        //获取用户有权限的功能项清单
        List<Feature> authFeatures = this.getUserAuthorizedFeatures(userId);
        //是全局管理员
        if (CollectionUtils.isNotEmpty(authFeatures)) {
            //循环构造键值对
            for (Feature feature : authFeatures) {
                //只添加操作功能项
                if (feature.getType() != 2) {
                    continue;
                }
                //添加功能项
//                result.get().put(feature.getCode(), feature.getUrl());
            }
        }
        return result;
    }

    /**
     * 清除用户权限相关的所有缓存
     */
    public void clearUserAuthorizedCaches(String userId) {
        if (StringUtils.isBlank(userId)) {
            return;
        }
        String pattern = "*" + userId;
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 获取一般用户的功能角色清单
     *
     * @param user 用户
     * @return 功能角色清单
     */
    public Set<Role> getNormalUserFeatureRoles(User user) {
        //获取用户的公共角色
        //一般用户的角色
        List<Role> authRoles = userRoleService.getEffectiveChildren(user.getId());
        return new HashSet<>(authRoles);
    }

    /**
     * 查询可分配的功能角色
     *
     * @param userId 用户id
     * @return 功能角色清单
     */
    public List<Role> getCanAssignedFeatureRoles(String userId) {
        //获取可分配的功能角色
        List<Role> canAssigned = roleService.getCanAssignedRoles();
        //获取已经分配的功能角色
        List<Role> assigned = userRoleService.getChildrenFromParentId(userId);
        Set<Role> result = new HashSet<>(canAssigned);
        result.removeAll(assigned);
        return new ArrayList<>(result);
    }

    /**
     * 获取用户是否有该页面的权限
     *
     * @param userId        用户Id
     * @param pageGroupCode 功能项页面分组代码(react页面路由)
     * @return 有权限则data返回有权限的功能项集合
     */
    @Cacheable(value = "UserAuthorizedFeature", key = "'UserAuthorizedFeature:'+#pageGroupCode+':'+#userId")
    public ResultData<Map<String, String>> getUserAuthorizedFeature(String userId, String pageGroupCode) {
        //判断参数不能为空
        if (StringUtils.isBlank(userId)) {
            return ResultData.fail(ContextUtil.getMessage("00067", "userId"));
        }
        if (StringUtils.isBlank(pageGroupCode)) {
            return ResultData.fail(ContextUtil.getMessage("00067", "pageGroupCode"));
        }
        //获取用户有权限的功能项清单
        Map<String, String> data = new HashMap<>();
        List<Feature> authFeatures = getUserAuthorizedFeatures(userId);
        //是全局管理员
        if (CollectionUtils.isNotEmpty(authFeatures)) {
            //循环构造键值对
            for (Feature feature : authFeatures) {
                //只添加操作功能项
                if (feature.getType() != 2) {
                    continue;
                }
//                if (StringUtils.equals(pageGroupCode, feature.getGroupCode())) {
//                    data.put(feature.getCode(), feature.getUrl());
//                }
            }
        }
        return ResultData.success(data);
    }

}