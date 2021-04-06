package com.changhong.sei.cicd.api;

import com.changhong.sei.cicd.dto.DeployConfigDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * 部署配置(DeployConfig)API
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@Valid
@FeignClient(name = "sei-manager", path = "deployConfig")
public interface DeployConfigApi extends BaseEntityApi<DeployConfigDto>, FindByPageApi<DeployConfigDto> {

    /**
     * 初始化部署
     *
     * @param id 业务实体id
     * @return 操作结果
     */
    @PostMapping(path = "initializeDeploy/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "初始化部署", notes = "按dev分支初始化部署")
    ResultData<Void> initializeDeploy(@PathVariable("id") String id);
}