package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.manager.dto.FeatureDto;
import com.changhong.sei.manager.dto.RoleDto;
import com.changhong.sei.manager.dto.RoleFeatureDto;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 实现功能: 功能角色分配的功能项API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:26
 */
@FeignClient(name = "sei-manager", path = "roleFeature")
public interface RoleFeatureApi extends BaseRelationApi<RoleFeatureDto, RoleDto, FeatureDto> {
}
