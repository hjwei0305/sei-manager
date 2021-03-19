package com.changhong.sei.ge.controller;

import com.changhong.sei.common.ObjectType;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.ge.api.ProjectUserApi;
import com.changhong.sei.ge.dto.ProjectUserDto;
import com.changhong.sei.ge.entity.Application;
import com.changhong.sei.ge.entity.ProjectUser;
import com.changhong.sei.ge.service.ApplicationService;
import com.changhong.sei.ge.service.ProjectUserService;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目用户(ProjectUser)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@RestController
@Api(value = "ProjectUserApi", tags = "项目用户服务")
@RequestMapping(path = "projectUser", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectUserController implements ProjectUserApi {
    /**
     * 服务器节点服务对象
     */
    @Autowired
    private ProjectUserService service;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 分配应用管理员
     *
     * @return 返回分配结果
     */
    @Override
    public ResultData<Void> assignAppAdmin(String account, String appId) {
        if (StringUtils.isBlank(account)) {
            return ResultData.fail("分配的账号不能为空");
        }
        if (StringUtils.isBlank(appId)) {
            return ResultData.fail("应用id不能为空.");
        }

        User user = userService.findByProperty(User.FIELD_ACCOUNT, account);
        if (Objects.isNull(user)) {
            return ResultData.fail("用户[" + account + "]不存在");
        }

        Application application = applicationService.findByProperty(Application.ID, appId);
        if (Objects.isNull(application)) {
            return ResultData.fail("应用[" + appId + "]不存在");
        }

        return service.assign(account, appId, application.getName(), ObjectType.APPLICATION);
    }

    /**
     * 批量分配应用模块用户
     *
     * @param users 用户
     * @return 返回分配结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> assign(List<ProjectUserDto> users) {
        List<ProjectUser> projectUsers = users.stream().map(o -> modelMapper.map(o, ProjectUser.class)).collect(Collectors.toList());
        return service.assign(projectUsers);
    }

    /**
     * 按用户账号清单移除应用模块用户
     *
     * @param objectId 应用模块id
     * @param accounts 用户账号
     * @return 操作结果
     */
    @Override
    public ResultData<Void> cancelAssign(String objectId, Set<String> accounts) {
        return service.cancelAssign(objectId, accounts);
    }

    /**
     * 获取未分配的用户
     *
     * @param objectId 对象id
     * @return 返回已分配的用户清单
     */
    @Override
    public PageResult<ProjectUserDto> getUnassignedUser(String objectId, Search search) {
        return service.getUnassignedUser(objectId, search);
    }

    /**
     * 获取已分配的用户
     *
     * @param objectId 对象id
     * @return 返回已分配的用户清单
     */
    @Override
    public ResultData<List<ProjectUserDto>> getAssignedUser(String objectId) {
        List<ProjectUserDto> result;
        List<ProjectUser> userLists = service.getAssignedUser(objectId);
        if (CollectionUtils.isNotEmpty(userLists)) {
            Set<String> accounts = userLists.stream().map(ProjectUser::getAccount).collect(Collectors.toSet());
            Search search = Search.createSearch();
            search.addFilter(new SearchFilter(User.FIELD_ACCOUNT, accounts, SearchFilter.Operator.IN));
            List<User> users = userService.findByFilters(search);
            Map<String, String> userMap = users.stream().collect(Collectors.toMap(User::getAccount, User::getNickname));
            result = userLists.stream().map(o -> {
                ProjectUserDto userDto = new ProjectUserDto();
                userDto.setAccount(o.getAccount());
                userDto.setUserName(userMap.get(o.getAccount()));
                return userDto;
            }).collect(Collectors.toList());
        } else {
            result = new ArrayList<>();
        }
        return ResultData.success(result);
    }
}