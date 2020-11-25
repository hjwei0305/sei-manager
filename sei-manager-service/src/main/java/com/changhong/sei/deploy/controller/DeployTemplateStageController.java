package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.deploy.api.DeployTemplateStageApi;
import com.changhong.sei.deploy.dto.DeployStageDto;
import com.changhong.sei.deploy.dto.DeployTemplateDto;
import com.changhong.sei.deploy.dto.DeployTemplateStageDto;
import com.changhong.sei.deploy.dto.DeployTemplateStageResponse;
import com.changhong.sei.deploy.entity.DeployStage;
import com.changhong.sei.deploy.entity.DeployTemplate;
import com.changhong.sei.deploy.entity.DeployTemplateStage;
import com.changhong.sei.deploy.service.DeployTemplateStageService;
import com.changhong.sei.exception.WebException;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * 保存业务实体
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<DeployTemplateStageDto> save(@Valid DeployTemplateStageDto dto) {
        if (Objects.isNull(dto)) {
            // 输入的数据传输对象为空！
            return ResultData.fail(ContextUtil.getMessage("core_service_00002"));
        }
        // 数据转换 to Entity
        DeployTemplateStage entity = convertToEntity(dto);
        OperateResultWithData<DeployTemplateStage> result;
        try {
            result = getService().save(entity);
        } catch (Exception e) {
            // 保存业务实体异常！
            throw new WebException(ContextUtil.getMessage("core_service_00003"), e);
        }
        if (result.notSuccessful()) {
            return ResultData.fail(result.getMessage());
        }
        // 数据转换 to DTO
        DeployTemplateStageDto resultData = convertRelationToDto(result.getData());
        return ResultData.success(result.getMessage(), resultData);
    }

    /**
     * 删除业务实体
     *
     * @param id 业务实体Id
     * @return 操作结果
     */
    @Override
    public ResultData delete(String id) {
        try {
            OperateResult result = getService().delete(id);
            return ResultDataUtil.convertFromOperateResult(result);
        } catch (Exception e) {
            // 删除业务实体异常！
            throw new WebException(ContextUtil.getMessage("core_service_00004"), e);
        }
    }

    /**
     * 通过Id获取一个业务实体
     *
     * @param id 业务实体Id
     * @return 业务实体
     */
    @Override
    public ResultData<DeployTemplateStageDto> findOne(String id) {
        DeployTemplateStage entity;
        try {
            entity = getService().findOne(id);
        } catch (Exception e) {
            // 获取业务实体异常！
            throw new WebException(ContextUtil.getMessage("core_service_00005"), e);
        }
        // 转换数据 to DTO
        DeployTemplateStageDto dto = convertRelationToDto(entity);
        return ResultData.success(dto);
    }

    /**
     * 通过模版Id获取阶段清单
     *
     * @param templateId 模版Id
     * @return 获取阶段清单
     */
    @Override
    public ResultData<List<DeployTemplateStageResponse>> getStageByTemplateId(String templateId) {
        return service.getStageByTemplateId(templateId);
    }
}