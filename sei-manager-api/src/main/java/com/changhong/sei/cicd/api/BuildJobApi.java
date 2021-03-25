package com.changhong.sei.cicd.api;

import com.changhong.sei.cicd.dto.*;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 构建任务(BuildJob)API
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@Valid
@FeignClient(name = "sei-manager", path = BuildJobApi.PATH)
public interface BuildJobApi extends BaseEntityApi<BuildJobDto>, FindByPageApi<BuildJobDto> {
    String PATH = "buildJob";

    /**
     * 分页查询发布申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @PostMapping(path = "findRequisitionByPage", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "分页查询发布申请单", notes = "分页查询发布申请单")
    ResultData<PageResult<BuildJobRequisitionDto>> findRequisitionByPage(@RequestBody Search search);

    /**
     * 创建发布申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "createRequisition", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "创建发布申请单", notes = "创建发布申请单")
    ResultData<BuildJobRequisitionDto> createRequisition(@RequestBody @Valid BuildJobDto dto);

    /**
     * 修改编辑发布申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "modifyRequisition", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "修改编辑发布申请单", notes = "修改编辑发布申请单")
    ResultData<BuildJobRequisitionDto> modifyRequisition(@RequestBody @Valid BuildJobDto dto);

    /**
     * 删除发布申请单
     *
     * @param id 申请单id
     * @return 操作结果
     */
    @DeleteMapping(path = "deleteRequisition/{id}")
    @ApiOperation(value = "删除发布申请单", notes = "删除发布申请单")
    ResultData<Void> deleteRequisition(@PathVariable("id") String id);

    /**
     * 构建Jenkins任务
     *
     * @param id 发布记录id
     * @return 返回构建操作
     */
    @PostMapping(path = "buildJob")
    @ApiOperation(value = "构建Jenkins任务", notes = "构建Jenkins任务")
    ResultData<Void> buildJob(@RequestParam("id") String id);

    /**
     * 获取构建明细
     *
     * @param id 发布记录id
     * @return 返回构建明细
     */
    @GetMapping(path = "getBuildDetail")
    @ApiOperation(value = "获取构建明细", notes = "获取构建明细")
    ResultData<BuildDetailDto> getBuildDetail(@RequestParam("id") String id);

    /**
     * Gitlab Push Hook
     *
     * @param request gitlab push hook
     * @return 返回结果
     */
    @PostMapping(path = "webhook")
    @ApiOperation(value = "Gitlab Push Hook", notes = "Gitlab Push Hook")
    ResultData<Void> webhook(@RequestBody GitlabPushHookRequest request);

    /**
     * 根据环境代码和应用模块id获取部署的tag与指定tag的变化记录
     *
     * @param envCode  环境代码
     * @param moduleId 应用模块id
     * @param tag      指定tag
     * @return 发挥tagName
     */
    @GetMapping(path = "getTags")
    @ApiOperation(value = "根据环境代码和应用模块id获取部署的tag与指定tag的变化记录", notes = "根据环境代码和应用模块id获取部署的tag与指定tag的变化记录")
    ResultData<List<TagDto>> getTags(@RequestParam("envCode") String envCode, @RequestParam("moduleId") String moduleId, @RequestParam("tag") String tag);
}