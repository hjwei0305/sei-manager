package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.DeployStageDto;
import com.changhong.sei.deploy.dto.DeployStageParamDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 部署阶段(DeployStage)API
 *
 * @author sei
 * @since 2020-11-23 08:34:03
 */
@Valid
@FeignClient(name = "sei-manager", path = "deployStage")
public interface DeployStageApi extends BaseEntityApi<DeployStageDto>, FindByPageApi<DeployStageDto>, FindAllApi<DeployStageDto> {

    /**
     * 获取阶段参数
     *
     * @param stageId 阶段id
     * @return 阶段参数
     */
    @GetMapping(path = "getStageParameters")
    @ApiOperation(value = "获取阶段参数", notes = "获取阶段参数")
    ResultData<List<DeployStageParamDto>> getStageParameters(@RequestParam(name = "stageId", required = false) String stageId);
}