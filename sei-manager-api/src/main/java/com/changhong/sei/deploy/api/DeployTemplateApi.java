package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.DeployTemplateDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * 部署模板(DeployTemplate)API
 *
 * @author sei
 * @since 2020-11-23 08:34:05
 */
@Valid
@FeignClient(name = "sei-manager", path = "deployTemplate")
public interface DeployTemplateApi extends BaseEntityApi<DeployTemplateDto>, FindByPageApi<DeployTemplateDto>, FindAllApi<DeployTemplateDto> {

    /**
     * 获取模板xml内容
     *
     * @param templateId 模板id
     * @return xml内容
     */
    @GetMapping(path = "getXml")
    @ApiOperation(value = "获取模板xml内容", notes = "获取模板xml内容")
    ResultData<String> getXml(@RequestParam("templateId") String templateId);
}