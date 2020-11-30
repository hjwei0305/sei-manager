package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.deploy.dto.ApplicationDto;
import com.changhong.sei.deploy.dto.ApplicationRequisitionDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 实现功能: 应用API
 */
@FeignClient(name = "sei-manager", path = "application")
public interface ApplicationApi extends BaseEntityApi<ApplicationDto>, FindByPageApi<ApplicationDto> {

    /**
     * 分页查询应用申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @PostMapping(path = "findRequisitionByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "分页查询应用申请单", notes = "分页查询应用申请单")
    ResultData<PageResult<ApplicationRequisitionDto>> findRequisitionByPage(@RequestBody Search search);

    /**
     * 创建应用申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "createRequisition", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "创建应用申请单", notes = "创建应用申请单")
    ResultData<Void> createRequisition(@RequestBody @Valid ApplicationDto dto);

    /**
     * 修改编辑应用申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "modifyRequisition", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改编辑应用申请单", notes = "修改编辑应用申请单")
    ResultData<Void> modifyRequisition(@RequestBody @Valid ApplicationDto dto);

    /**
     * 删除应用申请单
     *
     * @param id 申请单id
     * @return 操作结果
     */
    @DeleteMapping(path = "deleteRequisition/{id}")
    @ApiOperation(value = "删除应用申请单", notes = "删除应用申请单")
    ResultData<Void> deleteRequisition(@PathVariable("id") String id);
}
