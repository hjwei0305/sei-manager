package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.ConfigCompareResponse;
import com.changhong.sei.config.dto.ReleaseHistoryDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 发布历史(ConfReleaseHistory)API
 *
 * @author sei
 * @since 2021-02-22 21:44:15
 */
@Valid
@FeignClient(name = "sei-manager", path = ReleaseHistoryApi.PATH)
public interface ReleaseHistoryApi extends BaseEntityApi<ReleaseHistoryDto>, FindByPageApi<ReleaseHistoryDto> {
    String PATH = "releaseHistory";

    /**
     * 获取指定应用环境的发布版本清单
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 发布版本清单
     */
    @GetMapping(path = "getVersions/{appCode}/{envCode}")
    @ApiOperation(value = "获取一个业务实体", notes = "通过Id获取一个业务实体")
    ResultData<Set<String>> getVersions(@PathVariable("appCode") String appCode,
                                        @PathVariable("envCode") String envCode);

    /**
     * 跨环境比较已发布的配置(当前运行时态的配置)
     *
     * @param appCode    应用代码
     * @param currentEnv 当前环境代码
     * @param targetEnv  目标环境代码
     * @return 操作结果
     */
    @PostMapping(path = "crossEnvCompare/{appCode}/{currentEnv}/{targetEnv}")
    @ApiOperation(value = "跨环境比较已发布的配置(当前运行时态的配置)", notes = "跨环境比较已发布的配置(当前运行时态的配置)")
    ResultData<List<ConfigCompareResponse>> crossEnvCompare(@PathVariable("appCode") String appCode,
                                                            @PathVariable("currentEnv") String currentEnv,
                                                            @PathVariable("targetEnv") String targetEnv);
}