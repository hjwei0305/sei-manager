package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.manager.dto.FeatureDto;
import com.changhong.sei.manager.dto.FeatureNode;
import com.changhong.sei.manager.dto.RoleDto;
import com.changhong.sei.manager.dto.RoleFeatureDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 功能角色分配的功能项API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:26
 */
@FeignClient(name = "sei-manager", path = "roleFeature")
public interface RoleFeatureApi extends BaseRelationApi<RoleFeatureDto, RoleDto, FeatureDto> {

    /**
     * 获取已分配的角色功能项树
     *
     * @param roleId 角色id
     * @return 功能项树清单
     */
    @GetMapping(path = "getFeatureTree")
    @ApiOperation(notes = "获取角色的功能项树", value = "获取角色的功能项树(页面-功能项)")
    ResultData<List<FeatureNode>> getFeatureTree(@RequestParam("roleId") String roleId);

    /**
     * 获取未分配的功能项树
     *
     * @param roleId 角色id
     * @return 功能项树清单
     */
    @GetMapping(path = "getUnassignedFeatureTree")
    @ApiOperation(notes = "获取未分配的功能项树", value = "获取未分配的功能项树(页面-功能项)")
    ResultData<List<FeatureNode>> getUnassignedFeatureTree(@RequestParam("roleId") String roleId);

}
