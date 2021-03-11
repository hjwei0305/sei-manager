package com.changhong.sei.manager.controller;

import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.manager.api.UserRoleApi;
import com.changhong.sei.manager.dto.RoleDto;
import com.changhong.sei.manager.dto.UserDto;
import com.changhong.sei.manager.dto.UserRoleDto;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.entity.UserRole;
import com.changhong.sei.manager.service.UserRoleService;
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
@Api(value = "UserRoleApi", tags = "用户分配的功能角色API服务实现")
@RequestMapping(path = "userRole", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRoleController extends BaseRelationController<UserRole, User, Role, UserRoleDto, UserDto, RoleDto>
        implements UserRoleApi {
    @Autowired
    private UserRoleService service;

    @Override
    public BaseRelationService<UserRole, User, Role> getService() {
        return service;
    }

    /**
     * 将子数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public RoleDto convertChildToDto(Role entity) {
        return parentDtoModelMapper.map(entity, RoleDto.class);
    }

}
