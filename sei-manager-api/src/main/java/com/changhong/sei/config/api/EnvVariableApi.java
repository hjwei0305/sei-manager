package com.changhong.sei.config.api;

import com.changhong.sei.config.dto.EnvVariableDto;
import com.changhong.sei.config.dto.EnvVariableValueDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 环境变量(EnvVariable)API
 *
 * @author sei
 * @since 2021-03-02 14:26:33
 */
@Valid
@FeignClient(name = "sei-manager", path = EnvVariableApi.PATH)
public interface EnvVariableApi extends BaseEntityApi<EnvVariableDto> {
    String PATH = "envVariable";

    /**
     * 获取所有环境变量key列表
     *
     * @return key列表
     */
    @GetMapping(path = "getAllKey")
    @ApiOperation(value = "获取所有环境变量key列表", notes = "获取所有环境变量key列表")
    ResultData<List<EnvVariableDto>> getAllKey();

    /**
     * 获取所有启用的环境变量key列表
     *
     * @return key列表
     */
    @GetMapping(path = "getEnableKey")
    @ApiOperation(value = "获取所有启用的环境变量key列表", notes = "获取所有启用的环境变量key列表")
    ResultData<List<EnvVariableDto>> getEnableKey();

    /**
     * 通过key获取各个环境变量清单
     *
     * @return 环境变量清单
     */
    @GetMapping(path = "getVariableValues")
    @ApiOperation(value = "通过key获取各个环境变量清单", notes = "通过key获取各个环境变量清单")
    ResultData<List<EnvVariableValueDto>> getVariableValues(@RequestParam("code") String code);

    /**
     * 保存环境变量
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "saveVariableValue", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "保存环境变量", notes = "批量保存环境变量")
    ResultData<Void> saveVariableValue(@RequestBody @Valid List<EnvVariableValueDto> dto);

    /**
     * 删除环境变量
     *
     * @param ids 环境变量Id
     * @return 操作结果
     */
    @DeleteMapping(path = "deleteVariableValue")
    @ApiOperation(value = "删除环境变量", notes = "删除环境变量")
    ResultData<Void> deleteVariableValue(@RequestBody List<String> ids);
}