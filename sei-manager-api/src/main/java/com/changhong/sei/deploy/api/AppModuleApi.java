package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.deploy.dto.AppModuleDto;
import com.changhong.sei.deploy.dto.AppModuleRequisitionDto;
import com.changhong.sei.deploy.dto.ModuleUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 应用模块(AppModule)API
 *
 * @author sei
 * @since 2020-11-26 14:45:24
 */
@Valid
@FeignClient(name = "sei-manager", path = "appModule")
public interface AppModuleApi extends BaseEntityApi<AppModuleDto>, FindByPageApi<AppModuleDto> {

    /**
     * 分页查询应用模块申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @PostMapping(path = "findRequisitionByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "分页查询应用模块申请单", notes = "分页查询应用模块申请单")
    ResultData<PageResult<AppModuleRequisitionDto>> findRequisitionByPage(@RequestBody Search search);

    /**
     * 通过应用Id获取模块清单
     *
     * @param appId 应用id
     * @return 模块清单
     */
    @GetMapping(path = "findAppId")
    @ApiOperation(value = "通过应用Id获取模块清单", notes = "通过应用Id获取模块清单")
    ResultData<List<AppModuleDto>> findAppId(@RequestParam("appId") String appId);

    /**
     * 创建应用模块申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "createRequisition", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "创建应用模块申请单", notes = "创建应用模块申请单")
    ResultData<AppModuleRequisitionDto> createRequisition(@RequestBody @Valid AppModuleRequisitionDto dto);

    /**
     * 修改编辑应用模块申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "modifyRequisition", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改编辑应用模块申请单", notes = "修改编辑应用模块申请单")
    ResultData<AppModuleRequisitionDto> modifyRequisition(@RequestBody @Valid AppModuleRequisitionDto dto);

    /**
     * 删除应用模块申请单
     *
     * @param id 申请单id
     * @return 操作结果
     */
    @DeleteMapping(path = "deleteRequisition/{id}")
    @ApiOperation(value = "删除应用模块申请单", notes = "删除应用模块申请单")
    ResultData<Void> deleteRequisition(@PathVariable("id") String id);

    /**
     * 获取应用模块用户
     *
     * @param id 应用模块id
     * @return 操作结果
     */
    @GetMapping(path = "getModuleUsers")
    @ApiOperation(value = "获取应用模块用户清单", notes = "获取应用模块用户清单")
    ResultData<List<ModuleUser>> getModuleUsers(@RequestParam("id") String id);

    /**
     * 添加应用模块用户
     *
     * @param users 用户
     * @return 操作结果
     */
    @PostMapping(path = "addModuleUser", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加应用模块用户", notes = "添加应用模块用户")
    ResultData<Void> addModuleUser(@RequestBody Set<ModuleUser> users);

    /**
     * 按用户账号清单移除应用模块用户
     *
     * @param gitId      git项目id
     * @param gitUserIds git用户id
     * @return 操作结果
     */
    @DeleteMapping(path = "removeModuleUser/{gitId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "按用户账号清单移除应用模块用户", notes = "按用户账号清单移除应用模块用户")
    ResultData<Void> removeModuleUser(@PathVariable("gitId") String gitId, @RequestBody Set<Integer> gitUserIds);
}