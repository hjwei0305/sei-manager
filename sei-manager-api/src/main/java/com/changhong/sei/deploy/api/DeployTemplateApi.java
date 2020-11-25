package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.deploy.dto.DeployTemplateDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 部署模板(DeployTemplate)API
 *
 * @author sei
 * @since 2020-11-23 08:34:05
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-manager", path = "deployTemplate")
public interface DeployTemplateApi extends BaseEntityApi<DeployTemplateDto>, FindByPageApi<DeployTemplateDto>, FindAllApi<DeployTemplateDto> {

}