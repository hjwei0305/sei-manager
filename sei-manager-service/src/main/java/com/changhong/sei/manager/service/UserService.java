package com.changhong.sei.manager.service;

import com.changhong.sei.common.ThymeLeafHelper;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.integrated.service.GitlabService;
import com.changhong.sei.manager.commom.Constants;
import com.changhong.sei.manager.commom.EmailManager;
import com.changhong.sei.manager.commom.validatecode.IVerifyCodeGen;
import com.changhong.sei.manager.commom.validatecode.VerifyCode;
import com.changhong.sei.manager.dao.UserDao;
import com.changhong.sei.manager.dto.CheckUserRequest;
import com.changhong.sei.manager.dto.RegisteredUserRequest;
import com.changhong.sei.manager.entity.Feature;
import com.changhong.sei.manager.entity.Menu;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.vo.UserPrincipal;
import com.changhong.sei.util.HashUtil;
import com.changhong.sei.util.Signature;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.io.IOException;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailManager emailManager;
    @Autowired
    private UserGroupUserService userGroupUserService;
    @Autowired
    private IVerifyCodeGen verifyCodeGen;
    @Autowired
    private GitlabService gitlabService;

    /**
     * 开发运维平台的服务端地址(若有代理,配置代理后的地址)
     */
    @Value("${sei.server.host}")
    private String serverHost;
    @Value("${sei.server.web}")
    private String serverWeb;
    @Value("${sei.application.name:SEI开发运维平台}")
    private String managerName;

    @Override
    protected BaseEntityDao<User> getDao() {
        return dao;
    }

    /**
     * 验证码
     *
     * @param reqId 请求id
     * @return 返回验证码
     */
    public ResultData<String> generate(String reqId) {
        try {
            //设置长宽
            VerifyCode verifyCode = verifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            LogUtil.info("验证码: {}", code);

            // 验证码5分钟有效期
            cacheBuilder.set(Constants.REDIS_VERIFY_CODE_KEY + Signature.sign(reqId), code, (long) 5 * 60 * 1000);

            // 返回Base64编码过的字节数组字符串
            String str = Base64.encodeBase64String(verifyCode.getImgBytes());
            return ResultData.success("data:image/jpeg;base64," + str);
        } catch (IOException e) {
            LogUtil.error("验证码错误", e);
            return ResultData.fail("验证码错误");
        }
    }

    /**
     * 验证码
     *
     * @param reqId 请求id
     * @param code  校验值
     * @return 返回验证码
     */
    public ResultData<Void> check(String reqId, String code) {
        if (StringUtils.isBlank(code)) {
            return ResultData.fail("请输入验证码");
        }

        String cacheKey = Constants.REDIS_VERIFY_CODE_KEY + Signature.sign(reqId);
        String cacheValue = cacheBuilder.get(cacheKey);
        // 移除已使用的验证码
        cacheBuilder.remove(cacheKey);

        if (StringUtils.isBlank(cacheValue)) {
            return ResultData.fail("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(code, cacheValue)) {
            return ResultData.fail("验证码不正确");
        }
        return ResultData.success();
    }

    /**
     * 注册用户
     *
     * @param request 注册请求
     * @return 返回注册结果
     */
    public ResultData<Void> registered(RegisteredUserRequest request) {
        ResultData<Void> resultData = check(request.getReqId(), request.getVerifyCode());
        if (resultData.failed()) {
            return resultData;
        }

        String email = request.getEmail();
        User user = this.getByEmail(email);
        if (Objects.nonNull(user)) {
            return ResultData.fail("已存在[" + email + "]的账号");
        }

        String sign = Signature.sign(email);
        String cacheKey = Constants.REDIS_REGISTERED_KEY + sign;
//        String cacheValue = cacheBuilder.get(cacheKey);
//        if (StringUtils.isNotBlank(cacheValue)) {
//            return ResultData.fail("[" + email + "]已申请过,请勿重复申请");
//        }
        // 三天有效期
        cacheBuilder.set(cacheKey, email, 3600 * 24 * 3);

        Context context = new Context();
        context.setVariable("url", serverHost.concat("/user/activate/") + sign);
        String content = ThymeLeafHelper.getTemplateEngine().process("notify/ActivateUser.html", context);

        ResultData<Void> result = emailManager.sendMail(managerName + "-账号注册申请", content, email);
        if (result.successful()) {
            return ResultData.success("账号激活链接已发送到指定邮箱,请尽快完成激活.", null);
        } else {
            return result;
        }
    }

    /**
     * 账号注册申请激活
     *
     * @param sign 签名值
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> activate(String sign) {
        String cacheKey = Constants.REDIS_REGISTERED_KEY + sign;
        String email = cacheBuilder.get(cacheKey);
        if (StringUtils.isNotBlank(email)) {
            User user = this.getByEmail(email);
            if (Objects.nonNull(user)) {
                return ResultData.fail("已存在[" + email + "]的账号");
            }
            user = new User();
            ResultData<org.gitlab4j.api.models.User> resultData = gitlabService.getOptionalUserByEmail(email);
            if (resultData.successful()) {
                org.gitlab4j.api.models.User gitUser = resultData.getData();
                user.setAccount(gitUser.getUsername());
                user.setNickname(gitUser.getName());
                user.setEmail(gitUser.getEmail());
                user.setIsAdmin(gitUser.getIsAdmin());
                user.setCreateTime(System.currentTimeMillis());
            } else {
                String account = email.split("@")[0];
                user.setAccount(account);
                user.setNickname(account);
                user.setEmail(email);
                user.setCreateTime(System.currentTimeMillis());
            }
            ResultData<User> result = this.createUser(user);
            if (result.successful()) {
                return ResultData.success(serverWeb);
            } else {
                return ResultData.fail(result.getMessage());
            }
        }
        return ResultData.fail("激活失败,激活链接已失效.");
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        User user = dao.findByAccountOrEmailOrPhone(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone));
        List<Role> roles = roleService.getChildrenFromParentId(user.getId());
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
//                    //  通知被踢出的用户已被当前登录用户踢出，
//                    //  后期考虑使用 websocket 实现，具体伪代码实现如下。
//                    //  String message = "您已被用户【" + currentUsername + "】手动下线！";
//                    log.debug("用户【{}】被用户【{}】手动下线！", name, currentUsername);
//                });
//    }

    /**
     * 按邮箱获取用户
     *
     * @param email 邮箱
     * @return 返回用户
     */
    public User getByEmail(String email) {
        return dao.findByProperty(User.FIELD_EMAIL, email);
    }

    @Transactional
    public ResultData<User> createUser(User user) {
        String account = user.getAccount();
        String email = user.getEmail();
        String phone = user.getPhone();

        User exist;
        if (StringUtils.isNotBlank(account)) {
            exist = dao.findByAccountOrEmailOrPhone(account, account, account).orElse(null);
            if (Objects.nonNull(exist)) {
                return ResultData.fail(account + "已存在.");
            }
        }
        if (StringUtils.isNotBlank(email)) {
            exist = dao.findByAccountOrEmailOrPhone(email, email, email).orElse(null);
            if (Objects.nonNull(exist)) {
                return ResultData.fail(email + "已存在.");
            }
        }
        if (StringUtils.isNotBlank(phone)) {
            exist = dao.findByAccountOrEmailOrPhone(phone, phone, phone).orElse(null);
            if (Objects.nonNull(exist)) {
                return ResultData.fail(phone + "已存在.");
            }
        }

        // 生成8位随机密码
        String randomPass = RandomStringUtils.randomAlphanumeric(8);
        user.setPassword(passwordEncoder.encode(HashUtil.md5(randomPass)));
        long currentTimeMillis = System.currentTimeMillis();
        if (StringUtils.isBlank(user.getId())) {
            user.setCreateTime(currentTimeMillis);
        }
        user.setUpdateTime(currentTimeMillis);
        dao.save(user);

        Context context = new Context();
        context.setVariable("userName", user.getNickname());
        context.setVariable("account", user.getAccount());
        context.setVariable("password", randomPass);
        String content = ThymeLeafHelper.getTemplateEngine().process("notify/CreateUser.html", context);
        emailManager.sendMail(managerName + "-账号密码", content, user);
        try {
            ResultData<org.gitlab4j.api.models.User> resultData = gitlabService.getOptionalUserByEmail(email);
            if (resultData.failed()) {
                gitlabService.createUser(user.getId(), account, user.getNickname(), email);
            }
        } catch (Exception e) {
            LogUtil.error(email + " 创建gitlab账号异常", e);
        }
        return ResultData.success();
    }

    /**
     * 找回密码检查用户
     *
     * @param user
     */
    public ResultData<Void> checkUser(CheckUserRequest user) {
        return  ResultData.success();
    }

    /**
     * 主键删除
     *
     * @param id 主键
     * @return 返回操作结果对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResult delete(String id) {
        User user = dao.findOne(id);
        if (Objects.isNull(user)) {
            return OperateResult.operationFailure("用户不存在.");
        }

        user.setStatus(Boolean.FALSE);

        long currentTimeMillis = System.currentTimeMillis();
        user.setUpdateTime(currentTimeMillis);
        dao.save(user);
        return OperateResult.operationSuccess();
    }

    /**
     * 数据保存操作
     *
     * @param entity 持久化对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResultWithData<User> save(User entity) {
        if (Objects.isNull(entity)) {
            return OperateResultWithData.operationFailure("用户不能为空.");
        }
        if (entity.isNew()) {
            ResultData<User> resultData = this.createUser(entity);
            if (resultData.successful()) {
                return OperateResultWithData.operationSuccessWithData(resultData.getData());
            } else {
                return OperateResultWithData.operationFailure(resultData.getMessage());
            }
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            entity.setUpdateTime(currentTimeMillis);
            return super.save(entity);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> updatePassword(String usernameOrEmailOrPhone, String oldPassword, String password) {
        User user = dao.findByAccountOrEmailOrPhone(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone).orElse(null);
        if (Objects.isNull(user)) {
            return ResultData.fail("未找到用户信息 : " + usernameOrEmailOrPhone);
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResultData.fail("旧密码不正确,修改失败!");
        }

        user.setPassword(passwordEncoder.encode(password));
        long currentTimeMillis = System.currentTimeMillis();
        user.setUpdateTime(currentTimeMillis);
        this.save(user);
        return ResultData.success();
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
        long currentTimeMillis = System.currentTimeMillis();
        user.setUpdateTime(currentTimeMillis);
        this.save(user);
        return ResultData.success();
    }

    /**
     * 获取用户有权限的功能项清单
     *
     * @param userId 平台用户Id
     * @return 功能项清单
     */
    @Cacheable(value = "sei:manager:user:authorized:features", key = "#userId")
    public List<Feature> getUserAuthorizedFeatures(String userId) {
        List<Feature> result = new ArrayList<>();
        //获取用户
        User user = findOne(userId);
        // 管理员默认所有权限
        if (user == null || user.getIsAdmin()) {
            return result;
        }

        //一般用户的功能角色
        //获取用户授权的角色
        List<Role> authRoles = userRoleService.getChildrenFromParentId(user.getId());
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
//    @Cacheable(value = "sei:manager:user:authorized:menus", key = "#userId")
    public List<Menu> getUserAuthorizedMenus(String userId) {
        //获取用户
        User user = findOne(userId);
        if (Objects.isNull(user)) {
            throw new ServiceException("用户【" + userId + "】不存在！");
        }

        Set<Menu> userMenus = new HashSet<>();
        // 获取所有菜单
        List<Menu> allMenus = menuService.findAll();

        //判断管理员和一般用户
        if (user.getIsAdmin()) {
            userMenus.addAll(allMenus);
        } else {
            //--获取用户有权限的功能项清单
            List<Feature> userFeatures = this.getUserAuthorizedFeatures(userId);
            //通过功能项清单获取菜单节点
            List<Menu> memuNodes = MenuService.getMenusByFeatures(userFeatures, allMenus);
            if (CollectionUtils.isNotEmpty(memuNodes)) {
                userMenus.addAll(memuNodes);
                //拼接菜单关联的父节点
                MenuService.generateUserMenuNodes(userMenus, memuNodes, allMenus);
            }
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
//    @Cacheable(value = "UserCanAssignFeaturesCache", key = "'UserCanAssignFeatures:'+#userId")
    public List<Feature> getUserCanAssignFeatures(String userId) {
        List<Feature> result = new ArrayList<>();
        //获取用户
        User user = findOne(userId);
        if (user == null) {
            return result;
        }
        //判断全局管理员
        if (user.getIsAdmin()) {
            return featureService.findAll();
        }
        //一般用户的可分配功能角色
        List<Role> authRoles = userRoleService.getChildrenFromParentId(user.getId());
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

//    /**
//     * 获取用户前端权限检查的功能项键值
//     *
//     * @param userId 用户Id
//     * @return 功能项键值
//     */
////    @Cacheable(value = "UserAuthorizedFeatureMapsCache", key = "'UserAuthorizedFeatureMaps:'+#userId")
//    public Map<String, Set<String>> getUserAuthorizedFeatureMaps(String userId) {
//        Map<String, Set<String>> result = new HashMap<>();
//        // 获取用户有权限的功能项清单
//        List<Feature> authFeatures = this.getUserAuthorizedFeatures(userId);
//        if (CollectionUtils.isEmpty(authFeatures)) {
//            return new LinkedHashMap<>();
//        }
//        //循环构造键值对
//        for (Feature feature : authFeatures) {
//            // 只添加操作功能项
//            if (feature.getType() != 2) {
//                continue;
//            }
//            //判断分组代码键值是否存在
//            String key = feature.getId();
//            Set<String> codes = result.get(key);
//            if (Objects.isNull(codes)) {
//                codes = new LinkedHashSet<>();
//            }
//            //添加功能项代码
//            codes.add(feature.getPermission());
//            result.put(key, codes);
//        }
//        return result;
//    }
//
//    /**
//     * 获取用户前端权限检查的功能项键值
//     *
//     * @param userId 用户Id
//     * @return 功能项键值
//     */
//    public Map<String, Map<String, String>> getUserAuthorizedFeatureAppMaps(String userId) {
//        Map<String, Map<String, String>> result = new HashMap<>();
//        //获取用户有权限的功能项清单
//        List<Feature> authFeatures = this.getUserAuthorizedFeatures(userId);
//        //是全局管理员
//        if (CollectionUtils.isNotEmpty(authFeatures)) {
//            //循环构造键值对
//            for (Feature feature : authFeatures) {
//                //只添加操作功能项
//                if (feature.getType() != 2) {
//                    continue;
//                }
//                //添加功能项
////                result.get().put(feature.getCode(), feature.getUrl());
//            }
//        }
//        return result;
//    }

    /**
     * 清除用户权限相关的所有缓存
     */
    public void clearUserAuthorizedCaches(String userId) {
        if (StringUtils.isBlank(userId)) {
            return;
        }
//        String pattern = "*" + userId;
//        Set<String> keys = redisTemplate.keys(pattern);
//        if (keys != null && !keys.isEmpty()) {
//            redisTemplate.delete(keys);
//        }
    }

    /**
     * 根据用户组id 查询用户列表
     *
     * @param userGroupId 用户组id
     * @return 用户列表
     */
    public List<User> getChildrenFromParentId(String userGroupId) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(User.FIELD_ADMIN, Boolean.FALSE));
        search.addFilter(new SearchFilter(User.FIELD_STATUS, Boolean.TRUE));
        return dao.findByFilters(search);
//        return userGroupUserService.getChildrenFromParentId(userGroupId);
    }

}