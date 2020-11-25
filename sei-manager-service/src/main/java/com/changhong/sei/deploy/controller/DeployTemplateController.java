package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.api.DeployTemplateApi;
import com.changhong.sei.deploy.dto.DeployStageDto;
import com.changhong.sei.deploy.dto.DeployTemplateDto;
import com.changhong.sei.deploy.entity.DeployTemplate;
import com.changhong.sei.deploy.service.DeployTemplateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部署模板(DeployTemplate)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:03
 */
@RestController
@Api(value = "DeployTemplateApi", tags = "部署模板服务")
@RequestMapping(path = "deployTemplate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DeployTemplateController extends BaseEntityController<DeployTemplate, DeployTemplateDto> implements DeployTemplateApi {
    /**
     * 部署模板服务对象
     */
    @Autowired
    private DeployTemplateService service;

    @Override
    public BaseEntityService<DeployTemplate> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<DeployTemplateDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<DeployTemplateDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<DeployTemplateDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}