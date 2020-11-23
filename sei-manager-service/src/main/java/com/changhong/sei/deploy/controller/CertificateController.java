package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.api.CertificateApi;
import com.changhong.sei.deploy.dto.CertificateDto;
import com.changhong.sei.deploy.entity.Certificate;
import com.changhong.sei.deploy.service.CertificateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 凭证(Certificate)控制类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@RestController
@Api(value = "CertificateApi", tags = "部署凭证服务")
@RequestMapping(path = "certificate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CertificateController extends BaseEntityController<Certificate, CertificateDto> implements CertificateApi {
    /**
     * 凭证服务对象
     */
    @Autowired
    private CertificateService service;

    @Override
    public BaseEntityService<Certificate> getService() {
        return service;
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<CertificateDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<CertificateDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAll()));
    }
}