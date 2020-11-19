package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.manager.dto.RoleDto;
import com.changhong.sei.manager.dto.UserDto;
import com.changhong.sei.manager.dto.UserGroupDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 功能角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:56
 */
@FeignClient(name = "sei-manager", path = "userGroup")
public interface UserGroupApi extends BaseEntityApi<UserGroupDto> {

    /**
     * 获取所有用户组
     *
     * @return 用户组清单
     */
    @GetMapping(path = "findAll")
    @ApiOperation(value = "获取所有用户组", notes = "获取所有用户组")
    ResultData<List<UserGroupDto>> findAll();

    /**
     * 根据用户的id获取已分配的用户组
     *
     * @param userId 用户id
     * @return 用户组清单
     */
    @GetMapping(path = "getAssignedUserGroupsByeUser")
    @ApiOperation(value = "根据用户的id获取已分配的用户组", notes = "根据用户的id获取已分配的用户组")
    ResultData<List<UserGroupDto>> getAssignedUserGroupsByeUser(String userId);
}
