package com.changhong.sei.manager.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.api.DeployStageApi;
import com.changhong.sei.manager.dto.DeployStageDto;
import com.changhong.sei.manager.entity.DeployStage;
import com.changhong.sei.manager.service.DeployStageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部署阶段(DeployStage)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:01
 */
@RestController
@Api(value = "DeployStageApi", tags = "部署阶段服务")
@RequestMapping(path = "deployStage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DeployStageController extends BaseEntityController<DeployStage, DeployStageDto> implements DeployStageApi {
    /**
     * 部署阶段服务对象
     */
    @Autowired
    private DeployStageService service;

    @Override
    public BaseEntityService<DeployStage> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<DeployStageDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}