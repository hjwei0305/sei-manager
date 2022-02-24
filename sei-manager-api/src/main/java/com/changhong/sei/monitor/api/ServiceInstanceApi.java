package com.changhong.sei.monitor.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.monitor.dto.ServiceInstanceDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能： 服务实例API
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-27 13:29
 */
@FeignClient(name = "sei-agent", path = "serviceInstance")
public interface ServiceInstanceApi {

    /**
     * 获取当前所有可用应用服务清单
     */
    @GetMapping(path = "getServices")
    @ApiOperation(value = "获取当前所有可用应用服务清单", notes = "获取当前所有可用应用服务清单")
    ResultData<List<ServiceInstanceDto>> getServices(@RequestParam("env") String env);

    /**
     * 获取指定应用的实例清单
     *
     * @param serviceCode 应用代码
     * @return 返回指定应用的实例清单
     */
    @GetMapping(path = "getServiceInstance")
    @ApiOperation(value = "获取指定应用的实例清单", notes = "获取指定应用的实例清单")
    ResultData<List<ServiceInstanceDto>> getServiceInstance(@RequestParam("env") String env, @RequestParam("serviceCode") String serviceCode);

}
