package com.changhong.sei.manager.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.api.UserGroupApi;
import com.changhong.sei.manager.dto.UserGroupDto;
import com.changhong.sei.manager.entity.UserGroup;
import com.changhong.sei.manager.service.UserGroupService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 9:18
 */
@RestController
@Api(value = "UserGroupApi", tags = "用户组API服务实现")
@RequestMapping(path = "userGroup", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupController extends BaseEntityController<UserGroup, UserGroupDto> implements UserGroupApi {
    @Autowired
    private UserGroupService service;

    @Override
    public BaseEntityService<UserGroup> getService() {
        return service;
    }

    /**
     * 根据用户的id获取已分配的用户组
     *
     * @param userId 用户id
     * @return 用户组清单
     */
    @Override
    public ResultData<List<UserGroupDto>> getAssignedUserGroupsByeUser(String userId) {
        List<UserGroup> userGroups = service.getParentsFromChildId(userId);
        List<UserGroupDto> dtos = userGroups.stream().map(o -> dtoModelMapper.map(o, UserGroupDto.class)).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<UserGroupDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 保存多个用户组
     *
     * @param dtoList 用户组DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> saveList(@Valid List<UserGroupDto> dtoList) {
        List<UserGroup> groups = new ArrayList<>();
        UserGroup userGroup;
        long now = System.currentTimeMillis();
        for (UserGroupDto dto : dtoList) {
            userGroup = entityModelMapper.map(dto, UserGroup.class);
            userGroup.setCreateTime(now);
            userGroup.setUpdateTime(now);
            groups.add(userGroup);
        }
        service.save(groups);
        return ResultData.success();
    }
}
