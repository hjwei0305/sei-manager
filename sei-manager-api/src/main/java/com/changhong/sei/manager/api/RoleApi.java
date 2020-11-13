package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseEntityApi;
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
public interface RoleApi extends BaseEntityApi<RoleDto> {

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @GetMapping(path = "findAll")
    @ApiOperation(value = "获取所有功能角色", notes = "获取所有功能角色")
    ResultData<List<RoleDto>> findAll();

    /**
     * 根据功能角色的id获取已分配的用户
     *
     * @param roleId 功能角色的id
     * @return 用户清单
     */
    @GetMapping(path = "getAssignedUsersByeRole")
    @ApiOperation(value = "根据功能角色的id获取已分配的用户", notes = "根据功能角色的id获取已分配的用户")
    ResultData<List<UserDto>> getAssignedUsersByeRole(@RequestParam("roleId") String roleId);

}
