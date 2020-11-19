package com.changhong.sei.manager.controller;

import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.manager.api.UserGroupUserApi;
import com.changhong.sei.manager.api.UserRoleApi;
import com.changhong.sei.manager.dto.RoleDto;
import com.changhong.sei.manager.dto.UserDto;
import com.changhong.sei.manager.dto.UserGroupDto;
import com.changhong.sei.manager.dto.UserGroupUserDto;
import com.changhong.sei.manager.entity.*;
import com.changhong.sei.manager.service.UserGroupUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 用户分配的功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:26
 */
@RestController
@Api(value = "UserGroupUserApi", tags = "用户组分配的用户API服务实现")
@RequestMapping(path = "userGroupUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserGroupUserController extends BaseRelationController<UserGroupUser, UserGroup, User, UserGroupUserDto, UserGroupDto, UserDto>
        implements UserGroupUserApi {
    @Autowired
    private UserGroupUserService service;

    @Override
    public BaseRelationService<UserGroupUser, UserGroup, User> getService() {
        return service;
    }

    /**
     * 将子实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public UserDto convertChildToDto(User entity) {
        return parentDtoModelMapper.map(entity, UserDto.class);
    }
}
