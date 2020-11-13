package com.changhong.sei.manager.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.api.RoleApi;
import com.changhong.sei.manager.dto.RoleDto;
import com.changhong.sei.manager.dto.UserDto;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 9:18
 */
@RestController
@Api(value = "FeatureRoleApi", tags = "功能角色API服务实现")
@RequestMapping(path = "role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController extends BaseEntityController<Role, RoleDto> implements RoleApi {
    @Autowired
    private RoleService service;

    @Override
    public BaseEntityService<Role> getService() {
        return service;
    }

    /**
     * 根据功能角色的id获取已分配的用户
     *
     * @param roleId 功能角色的id
     * @return 用户清单
     */
    @Override
    public ResultData<List<UserDto>> getAssignedUsersByeRole(String roleId) {
        List<User> users = service.getParentsFromChildId(roleId);
        List<UserDto> dtos = users.stream().map(o -> dtoModelMapper.map(o, UserDto.class)).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<RoleDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

}
