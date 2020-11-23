package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.manager.dto.DeployStageDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 部署阶段(DeployStage)API
 *
 * @author sei
 * @since 2020-11-23 08:34:03
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-manager", path = "deployStage")
public interface DeployStageApi extends BaseEntityApi<DeployStageDto>, FindByPageApi<DeployStageDto> {

}