package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.DeployStageDto;
import com.changhong.sei.deploy.dto.DeployTemplateDto;
import com.changhong.sei.deploy.dto.DeployTemplateStageDto;
import com.changhong.sei.deploy.dto.DeployTemplateStageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 部署模板阶段关系表(DeployTemplateStage)API
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-manager", path = "deployTemplateStage")
public interface DeployTemplateStageApi extends BaseEntityApi<DeployTemplateStageDto>, BaseRelationApi<DeployTemplateStageDto, DeployTemplateDto, DeployStageDto> {

    /**
     * 通过模版Id获取阶段清单
     *
     * @param templateId 模版Id
     * @return 获取阶段清单
     */
    @GetMapping(path = "getStageByTemplateId")
    @ApiOperation(value = "通过模版Id获取阶段清单", notes = "通过模版Id获取阶段清单")
    ResultData<List<DeployTemplateStageResponse>> getStageByTemplateId(@RequestParam("templateId") String templateId);
}