package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.api.RequisitionOrderApi;
import com.changhong.sei.deploy.dto.ApprovalCancelRequest;
import com.changhong.sei.deploy.dto.ApprovalRejectRequest;
import com.changhong.sei.deploy.dto.ApprovalSubmitRequest;
import com.changhong.sei.deploy.service.RequisitionOrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 申请记录(RequisitionRecord)控制类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@RestController
@Api(value = "RequisitionRecordApi", tags = "申请审核服务")
@RequestMapping(path = "requisition", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RequisitionOrderController implements RequisitionOrderApi {
    /**
     * 申请记录服务对象
     */
    @Autowired
    private RequisitionOrderService service;

    /**
     * 提交申请单
     *
     * @param submitRequest 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> submit(@Valid ApprovalSubmitRequest submitRequest) {
        return service.submit(submitRequest);
    }

    /**
     * 驳回申请单
     *
     * @param rejectRequest 驳回请求
     * @return 操作结果
     */
    @Override
    public ResultData<Void> reject(@Valid ApprovalRejectRequest rejectRequest) {
        return service.reject(rejectRequest);
    }

    /**
     * 取消(终止)申请单
     *
     * @param cancelRequest 取消(终止)请求
     * @return 操作结果
     */
    @Override
    public ResultData<Void> cancel(@Valid ApprovalCancelRequest cancelRequest) {
        return service.cancel(cancelRequest);
    }
}