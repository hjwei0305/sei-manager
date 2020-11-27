package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.ApplicationDto;
import com.changhong.sei.deploy.dto.ApplicationResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 应用API
 */
@FeignClient(name = "sei-manager", path = "application")
public interface ApplicationApi extends FindByPageApi<ApplicationDto> {

}
