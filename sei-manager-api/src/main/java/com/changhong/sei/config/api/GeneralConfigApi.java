package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.GeneralConfigDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 全局参数配置(ConfGlobalConfig)API
 *
 * @author sei
 * @since 2021-02-22 21:44:08
 */
@Valid
@FeignClient(name = "sei-manager", path = GeneralConfigApi.PATH)
public interface GeneralConfigApi extends BaseEntityApi<GeneralConfigDto> {
    String PATH = "generalConfig";


    /**
     * 通过环境代码获取通用配置清单
     *
     * @param envCode 环境代码
     * @return 业务实体
     */
    @GetMapping(path = "findByEnv")
    @ApiOperation(value = "通过环境代码获取指定环境的通用配置清单", notes = "通过环境代码获取指定环境的通用配置清单")
    ResultData<List<GeneralConfigDto>> findByEnv(@RequestParam("envCode") String envCode);
}