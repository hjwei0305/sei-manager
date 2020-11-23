package com.changhong.sei.manager.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.manager.api.DeployTemplateStageApi;
import com.changhong.sei.manager.dto.DeployStageDto;
import com.changhong.sei.manager.dto.DeployTemplateDto;
import com.changhong.sei.manager.dto.DeployTemplateStageDto;
import com.changhong.sei.manager.entity.DeployStage;
import com.changhong.sei.manager.entity.DeployTemplate;
import com.changhong.sei.manager.entity.DeployTemplateStage;
import com.changhong.sei.manager.service.DeployTemplateStageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部署模板阶段关系表(DeployTemplateStage)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:07
 */
@RestController
@Api(value = "DeployTemplateStageApi", tags = "部署模板阶段关系表服务")
@RequestMapping(path = "deployTemplateStage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DeployTemplateStageController extends BaseRelationController<DeployTemplateStage, DeployTemplate, DeployStage, DeployTemplateStageDto, DeployTemplateDto, DeployStageDto> implements DeployTemplateStageApi {
    /**
     * 部署模板阶段关系表服务对象
     */
    @Autowired
    private DeployTemplateStageService service;

    @Override
    public BaseRelationService<DeployTemplateStage, DeployTemplate, DeployStage> getService() {
        return service;
    }

}