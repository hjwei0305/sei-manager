package com.changhong.sei.cicd.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.cicd.api.DeployConfigApi;
import com.changhong.sei.cicd.dto.DeployConfigDto;
import com.changhong.sei.cicd.entity.DeployConfig;
import com.changhong.sei.cicd.service.DeployConfigService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部署配置(DeployConfig)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@RestController
@Api(value = "DeployConfigApi", tags = "部署配置服务")
@RequestMapping(path = "deployConfig", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeployConfigController extends BaseEntityController<DeployConfig, DeployConfigDto> implements DeployConfigApi {
    /**
     * 服务器节点服务对象
     */
    @Autowired
    private DeployConfigService service;

    @Override
    public BaseEntityService<DeployConfig> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<DeployConfigDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }

    /**
     * 初始化部署
     *
     * @param id 业务实体id
     * @return 操作结果
     */
    @Override
    public ResultData<Void> initializeDeploy(String id) {
        return service.initializeDeploy(id);
    }
}