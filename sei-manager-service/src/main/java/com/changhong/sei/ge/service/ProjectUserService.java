package com.changhong.sei.ge.service;

import com.changhong.sei.common.ObjectType;
import com.changhong.sei.core.cache.CacheBuilder;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.dao.ProjectUserDao;
import com.changhong.sei.ge.dto.ProjectUserDto;
import com.changhong.sei.ge.entity.AppModule;
import com.changhong.sei.ge.entity.Application;
import com.changhong.sei.ge.entity.ProjectGroup;
import com.changhong.sei.ge.entity.ProjectUser;
import com.changhong.sei.integrated.service.GitlabService;
import com.changhong.sei.manager.commom.Constants;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.service.UserService;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目用户(ProjectUser)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service
public class ProjectUserService extends BaseEntityService<ProjectUser> implements Constants {
    @Autowired
    private ProjectUserDao dao;
    @Autowired
    private UserService userService;
    @Autowired
    private GitlabService gitlabService;
    @Autowired
    private ProjectGroupService projectGroupService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private AppModuleService moduleService;
    @Autowired
    private CacheBuilder cacheBuilder;

    @Override
    protected BaseEntityDao<ProjectUser> getDao() {
        return dao;
    }

    /**
     * 分配应用管理员
     *
     * @return 返回分配结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> assign(String account, String objectId, String objectName, ObjectType type) {
        if (StringUtils.isBlank(account)) {
            return ResultData.fail("分配的账号不能为空");
        }
        if (StringUtils.isBlank(objectId)) {
            return ResultData.fail("对象id不能为空.");
        }
        if (Objects.isNull(type)) {
            return ResultData.fail("对象类型不能为空.");
        }
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(ProjectUser.FIELD_OBJECT_ID, objectId));
        search.addFilter(new SearchFilter(ProjectUser.FIELD_ACCOUNT, account));
        search.addFilter(new SearchFilter(ProjectUser.FIELD_TYPE, type));
        ProjectUser user = dao.findOneByFilters(search);
        if (Objects.isNull(user)) {
            user = new ProjectUser();
            user.setType(type);
            user.setObjectId(objectId);
            user.setObjectName(objectName);
            user.setAccount(account);

//            if (ObjectType.MODULE == type) {
//                AppModule module = moduleService.findOne(user.getObjectId());
//                if (Objects.nonNull(module)) {
//                    objectName = module.getName();
//                    ResultData<Integer> resultData = gitlabService.addProjectUser(module.getGitId(), account);
//                    if (resultData.successful()) {
//                        user.setGitId(resultData.getData());
//                        user.setObjectName(objectName);
//                        this.save(user);
//                    } else {
//                        return ResultData.fail(resultData.getMessage());
//                    }
//                } else {
//                    return ResultData.fail("应用模块不存在.");
//                }
//            } else {
                this.save(user);
//            }
        }
        return ResultData.success();
    }

    /**
     * 批量分配应用模块用户
     *
     * @param users 用户
     * @return 返回分配结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> assign(List<ProjectUser> users) {
        if (CollectionUtils.isNotEmpty(users)) {
            Set<String> objIds = users.stream().map(ProjectUser::getObjectId).collect(Collectors.toSet());
            Search search = Search.createSearch();
            search.addFilter(new SearchFilter(ProjectUser.FIELD_OBJECT_ID, objIds, SearchFilter.Operator.IN));
            List<ProjectUser> userList = dao.findByFilters(search);
            Set<String> accounts = userList.stream().map(u -> u.getAccount() + "|" + u.getType().name()).collect(Collectors.toSet());

            search.clearAll();
            search.addFilter(new SearchFilter(AppModule.ID, objIds, SearchFilter.Operator.IN));
            List<AppModule> moduleList = moduleService.findByFilters(search);
            Map<String, String> moduleMap = moduleList.stream().collect(Collectors.toMap(AppModule::getId, AppModule::getName));

            String account;
            ObjectType type;
            ResultData<Void> result;
            for (ProjectUser user : users) {
                account = user.getAccount();
                type = user.getType();
                if (StringUtils.isNotBlank(account) && Objects.nonNull(type)
                        && !accounts.contains(account + "|" + type.name())) {
                    result = this.assign(account, user.getObjectId(), moduleMap.get(user.getObjectId()), type);
                    if (result.failed()) {
                        // 事务回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return result;
                    }
                }
            }
            return ResultData.success();
        } else {
            return ResultData.fail("分配的用户不能为空.");
        }
    }

    /**
     * 按用户账号清单移除应用模块用户
     *
     * @param objectId 应用模块id
     * @param accounts 用户账号
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> cancelAssign(String objectId, Set<String> accounts) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(ProjectUser.FIELD_OBJECT_ID, objectId));
        search.addFilter(new SearchFilter(ProjectUser.FIELD_ACCOUNT, accounts, SearchFilter.Operator.IN));
        List<ProjectUser> users = dao.findByFilters(search);
        if (CollectionUtils.isNotEmpty(users)) {
            if (ObjectType.MODULE == users.get(0).getType()) {
                AppModule module = moduleService.findOne(objectId);
                if (Objects.isNull(module)) {
                    return ResultData.fail("应用模块[" + objectId + "]不存在.");
                }
                // 移除gitlab用户
                gitlabService.removeProjectUser(module.getGitId(), users.stream().map(ProjectUser::getGitId).distinct().toArray(Integer[]::new));
            }
            Set<String> ids = users.stream().map(ProjectUser::getId).collect(Collectors.toSet());
            this.delete(ids);
        }
        return ResultData.success();
    }

    /**
     * 获取未分配的用户
     *
     * @param objectId 对象id
     * @return 返回已分配的用户清单
     */
    public PageResult<ProjectUserDto> getUnassignedUser(String objectId, Search searchUser) {
        List<ProjectUser> projectUsers = dao.findListByProperty(ProjectUser.FIELD_OBJECT_ID, objectId);
        Set<String> accounts = projectUsers.stream().map(ProjectUser::getAccount).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(accounts)) {
            searchUser.addFilter(new SearchFilter(User.FIELD_ACCOUNT, accounts, SearchFilter.Operator.NOTIN));
        }
        PageResult<User> pageResult = userService.findByPage(searchUser);
        PageResult<ProjectUserDto> userPageResult = new PageResult<>(pageResult);
        if (pageResult.getTotal() > 0) {
            List<ProjectUserDto> list = pageResult.getRows().stream().map(o -> {
                ProjectUserDto user = new ProjectUserDto();
                user.setAccount(o.getAccount());
                user.setUserName(o.getUserName());
                return user;
            }).collect(Collectors.toList());
            userPageResult.setRows(list);
        }
        return userPageResult;
    }

    /**
     * 获取已分配的用户
     *
     * @param objectId 对象id
     * @return 返回已分配的用户清单
     */
    public List<ProjectUser> getAssignedUser(String objectId) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(ProjectUser.FIELD_OBJECT_ID, objectId));
        return dao.findByFilters(search);
    }

    /**
     * 获取有权限的项目组id
     * 数据权限检查用
     *
     * @param account 账号
     * @return 返回有权限的项目组id
     */
    public Set<String> getAssignedGroupIds(String account) {
        Set<String> groupIds;
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(ProjectUser.FIELD_ACCOUNT, account));
        search.addFilter(new SearchFilter(ProjectUser.FIELD_TYPE, ObjectType.PROJECT));
        List<ProjectUser> userList = dao.findByFilters(search);
        if (CollectionUtils.isNotEmpty(userList)) {
            // 有权限的项目组
            groupIds = userList.stream().map(ProjectUser::getObjectId).collect(Collectors.toSet());
        } else {
            groupIds = new HashSet<>();
        }
        return groupIds;
    }

    /**
     * 获取有权限的应用id
     * 数据权限检查用
     *
     * @param account 账号
     * @return 返回有权限的应用id
     */
    public Set<String> getAssignedAppIds(String account) {
        Set<String> appIdSet = cacheBuilder.get(REDIS_AUTHORIZED_APP_KEY + account);
        if (CollectionUtils.isEmpty(appIdSet)) {
            appIdSet = new HashSet<>();
            Search search = Search.createSearch();
            search.addFilter(new SearchFilter(ProjectUser.FIELD_ACCOUNT, account));
            search.addFilter(new SearchFilter(ProjectUser.FIELD_TYPE, Sets.newHashSet(ObjectType.PROJECT, ObjectType.APPLICATION), SearchFilter.Operator.IN));
            List<ProjectUser> userList = dao.findByFilters(search);
            if (CollectionUtils.isNotEmpty(userList)) {
                // 获取有权限的应用
                appIdSet.addAll(userList.stream().filter(u -> ObjectType.APPLICATION == u.getType()).map(ProjectUser::getObjectId).collect(Collectors.toSet()));
                // 有权限的项目组
                Set<String> groupIds = userList.stream().filter(u -> ObjectType.PROJECT == u.getType()).map(ProjectUser::getObjectId).collect(Collectors.toSet());
                if (CollectionUtils.isNotEmpty(groupIds)) {
                    List<ProjectGroup> groupList = new ArrayList<>();
                    for (String groupId : groupIds) {
                        // 获取子组
                        groupList.addAll(projectGroupService.getChildrenNodes(groupId));
                    }
                    if (CollectionUtils.isNotEmpty(groupList)) {
                        Set<String> groupCodes = groupList.stream().map(ProjectGroup::getCode).collect(Collectors.toSet());
                        search.clearAll();
                        search.addFilter(new SearchFilter(Application.FIELD_GROUP_CODE, groupCodes, SearchFilter.Operator.IN));
                        List<Application> applicationList = applicationService.findByFilters(search);
                        if (CollectionUtils.isNotEmpty(applicationList)) {
                            // 添加项目组所属应用
                            appIdSet.addAll(applicationList.stream().map(Application::getId).collect(Collectors.toSet()));
                        }
                    }
                }
                cacheBuilder.set(REDIS_AUTHORIZED_APP_KEY + account, appIdSet, 86400);
            }
        }
        return appIdSet;
    }

    /**
     * 获取有权限的应用模块id
     * 数据权限检查用
     *
     * @param account 账号
     * @return 返回有权限的应用模块id
     */
    public Set<String> getAssignedModuleIds(String account) {
        Set<String> moduleIds = cacheBuilder.get(REDIS_AUTHORIZED_MODULE_KEY + account);
        if (CollectionUtils.isEmpty(moduleIds)) {
            moduleIds = new HashSet<>();
            List<ProjectUser> userList = dao.findListByProperty(ProjectUser.FIELD_ACCOUNT, account);
            if (CollectionUtils.isNotEmpty(userList)) {
                Search search = Search.createSearch();
                // 获取有权限的应用
                Set<String> appIdSet = userList.stream().filter(u -> ObjectType.APPLICATION == u.getType()).map(ProjectUser::getObjectId).collect(Collectors.toSet());
                // 有权限的项目组
                Set<String> groupIds = userList.stream().filter(u -> ObjectType.PROJECT == u.getType()).map(ProjectUser::getObjectId).collect(Collectors.toSet());
                if (CollectionUtils.isNotEmpty(groupIds)) {
                    List<ProjectGroup> groupList = new ArrayList<>();
                    for (String groupId : groupIds) {
                        // 获取子组
                        groupList.addAll(projectGroupService.getChildrenNodes(groupId));
                    }
                    if (CollectionUtils.isNotEmpty(groupList)) {
                        Set<String> groupCodes = groupList.stream().map(ProjectGroup::getCode).collect(Collectors.toSet());
                        search.clearAll();
                        search.addFilter(new SearchFilter(Application.FIELD_GROUP_CODE, groupCodes, SearchFilter.Operator.IN));
                        List<Application> applicationList = applicationService.findByFilters(search);
                        if (CollectionUtils.isNotEmpty(applicationList)) {
                            // 添加项目组所属应用
                            appIdSet.addAll(applicationList.stream().map(Application::getId).collect(Collectors.toSet()));
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(appIdSet)) {
                    // 添加应用所属的应用模块
                    search.clearAll();
                    search.addFilter(new SearchFilter(AppModule.FIELD_APP_ID, appIdSet, SearchFilter.Operator.IN));
                    List<AppModule> moduleList = moduleService.findByFilters(search);
                    if (CollectionUtils.isNotEmpty(moduleList)) {
                        moduleIds.addAll(moduleList.stream().map(AppModule::getId).collect(Collectors.toSet()));
                    }
                }
                // 应用模块id
                moduleIds.addAll(userList.stream().filter(u -> ObjectType.MODULE == u.getType()).map(ProjectUser::getObjectId).collect(Collectors.toSet()));
            }
            cacheBuilder.set(REDIS_AUTHORIZED_MODULE_KEY + account, moduleIds, 86400);
        }
        return moduleIds;
    }
}