package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.deploy.dto.AppModuleDto;
import com.changhong.sei.deploy.dto.AppModuleRequisitionDto;
import com.changhong.sei.deploy.dto.CreateTagRequest;
import com.changhong.sei.deploy.dto.GitlabTagDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    ResultData<AppModuleRequisitionDto> createRequisition(@RequestBody @Valid AppModuleDto dto);

    /**
     * 修改编辑应用模块申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "modifyRequisition", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改编辑应用模块申请单", notes = "修改编辑应用模块申请单")
    ResultData<AppModuleRequisitionDto> modifyRequisition(@RequestBody @Valid AppModuleDto dto);

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
     * 获取项目标签
     *
     * @param gitId git项目id
     * @return 创建结果
     */
    @GetMapping(path = "getTags/{gitId}")
    @ApiOperation(value = "获取项目标签", notes = "获取项目标签")
    ResultData<List<GitlabTagDto>> getTags(@PathVariable("gitId") String gitId);

    /**
     * 创建标签
     *
     * @param request 创建标签请求
     * @return 创建结果
     */
    @PostMapping(path = "createTag")
    @ApiOperation(value = "创建标签", notes = "创建标签")
    ResultData<GitlabTagDto> createTag(@RequestBody CreateTagRequest request);

    /**
     * 删除标签
     *
     * @param gitId   git项目id
     * @param tagName tag名
     * @return 创建结果
     */
    @DeleteMapping(path = "deleteRelease/{gitId}/{tagName}")
    @ApiOperation(value = "删除标签", notes = "删除标签")
    ResultData<Void> deleteRelease(@PathVariable("gitId") String gitId, @PathVariable("tagName") String tagName);
}