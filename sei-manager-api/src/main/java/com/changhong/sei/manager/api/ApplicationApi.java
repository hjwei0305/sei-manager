package com.changhong.sei.manager.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.manager.dto.ApplicationDto;
import com.changhong.sei.manager.dto.ApplicationResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 应用API
 */
@FeignClient(name = "sei-manager", path = "application")
public interface ApplicationApi {
    /**
     * 获取当前所有可用应用服务清单
     */
    @GetMapping(path = "getServiceCodes")
    @ApiOperation(value = "获取当前所有可用应用服务清单", notes = "获取当前所有可用应用服务清单")
    ResultData<List<String>> getServiceCodes();

    /**
     * 获取当前所有可用应用服务清单
     */
    @GetMapping(path = "getServices")
    @ApiOperation(value = "获取当前所有可用应用服务清单", notes = "获取当前所有可用应用服务清单")
    ResultData<List<ApplicationDto>> getServices();

    /**
     * 获取指定应用的实例清单
     *
     * @param serviceCode 应用代码
     * @return 返回指定应用的实例清单
     */
    @GetMapping(path = "getServiceInstance")
    @ApiOperation(value = "获取指定应用的实例清单", notes = "获取指定应用的实例清单")
    ResultData<List<ApplicationResponse>> getServiceInstance(@RequestParam("serviceCode") String serviceCode);
}
