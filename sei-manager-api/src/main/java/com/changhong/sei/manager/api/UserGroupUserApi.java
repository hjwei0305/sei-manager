package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.manager.dto.*;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 实现功能: 用户分配的功能角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:24
 */
@FeignClient(name = "sei-manager", path = "userGroupUser")
public interface UserGroupUserApi extends BaseRelationApi<UserGroupUserDto, UserGroupDto, UserDto> {

}
