package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.manager.dto.RoleDto;
import com.changhong.sei.manager.dto.UserDto;
import com.changhong.sei.manager.dto.UserRoleDto;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 实现功能: 用户分配的功能角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:24
 */
@FeignClient(name = "sei-manager", path = "userRole")
public interface UserRoleApi extends BaseRelationApi<UserRoleDto, UserDto, RoleDto> {

}
