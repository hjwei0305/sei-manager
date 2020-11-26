package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.api.RequisitionRecordApi;
import com.changhong.sei.deploy.dto.RequisitionRecordDto;
import com.changhong.sei.deploy.entity.RequisitionRecord;
import com.changhong.sei.deploy.service.RequisitionRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 申请记录(RequisitionRecord)控制类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@RestController
@Api(value = "RequisitionRecordApi", tags = "申请记录服务")
@RequestMapping(path = "requisition", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RequisitionRecordController extends BaseEntityController<RequisitionRecord, RequisitionRecordDto> implements RequisitionRecordApi {
    /**
     * 申请记录服务对象
     */
    @Autowired
    private RequisitionRecordService service;

    @Override
    public BaseEntityService<RequisitionRecord> getService() {
        return service;
    }

}