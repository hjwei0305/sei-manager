package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.deploy.dto.DeployStageDto;
import com.changhong.sei.deploy.dto.DeployTemplateDto;
import com.changhong.sei.deploy.dto.DeployTemplateStageDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 部署模板阶段关系表(DeployTemplateStage)API
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-manager", path = "deployTemplateStage")
public interface DeployTemplateStageApi extends BaseRelationApi<DeployTemplateStageDto, DeployTemplateDto, DeployStageDto> {

}