package com.changhong.sei.deploy.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.ApprovalCancelRequest;
import com.changhong.sei.deploy.dto.ApprovalRejectRequest;
import com.changhong.sei.deploy.dto.ApprovalSubmitRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 申请记录(RequisitionOrder)API
 *
 * @author sei
 * @since 2020-11-26 14:45:24
 */
@Valid
@FeignClient(name = "sei-manager", path = "requisition")
public interface RequisitionRecordApi {

    /**
     * 提交申请单
     *
     * @param submitRequest 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "submit", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "提交申请单", notes = "提交申请单")
    ResultData<Void> submit(@RequestBody @Valid ApprovalSubmitRequest submitRequest);

    /**
     * 驳回申请单
     *
     * @param rejectRequest 驳回请求
     * @return 操作结果
     */
    @PostMapping(path = "reject", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "驳回申请单", notes = "驳回申请单")
    ResultData<Void> reject(@RequestBody @Valid ApprovalRejectRequest rejectRequest);

    /**
     * 取消(终止)申请单
     *
     * @param cancelRequest 取消(终止)请求
     * @return 操作结果
     */
    @PostMapping(path = "cancel", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "取消(终止)申请单", notes = "取消(终止)申请单")
    ResultData<Void> cancel(@RequestBody @Valid ApprovalCancelRequest cancelRequest);
}