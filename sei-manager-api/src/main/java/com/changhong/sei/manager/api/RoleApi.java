package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.manager.dto.RoleDto;
import com.changhong.sei.manager.dto.UserDto;
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
@FeignClient(name = "sei-manager", path = "role")
public interface RoleApi extends BaseEntityApi<RoleDto>, FindByPageApi<RoleDto> {

    /**
     * 根据功能角色的id获取已分配的用户
     *
     * @param roleId 功能角色的id
     * @return 用户清单
     */
    @GetMapping(path = "getAssignedUsersByeRole")
    @ApiOperation(value = "根据功能角色的id获取已分配的用户", notes = "根据功能角色的id获取已分配的用户")
    ResultData<List<UserDto>> getAssignedUsersByeRole(@RequestParam("roleId") String roleId);

    /**
     * 获取用户本人可以分配的角色
     *
     * @return 可以分配的角色
     */
    @GetMapping(path = "getCanAssignedRoles")
    @ApiOperation(value = "根据角色组Id获取可分配的角色", notes = "根据角色组Id获取用户本人可以分配的角色")
    ResultData<List<RoleDto>> getCanAssignedRoles();
}
