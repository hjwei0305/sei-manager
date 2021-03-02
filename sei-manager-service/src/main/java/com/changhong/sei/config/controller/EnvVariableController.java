package com.changhong.sei.config.controller;

import com.changhong.sei.config.api.EnvVariableApi;
import com.changhong.sei.config.dto.EnvVariableDto;
import com.changhong.sei.config.entity.EnvVariable;
import com.changhong.sei.config.service.EnvVariableService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 环境变量(ConfEnvVariable)控制类
 *
 * @author sei
 * @since 2021-03-02 14:26:29
 */
@RestController
@Api(value = "EnvVariableApi", tags = "环境变量服务")
@RequestMapping(path = EnvVariableApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class EnvVariableController extends BaseEntityController<EnvVariable, EnvVariableDto> implements EnvVariableApi {
    /**
     * 环境变量服务对象
     */
    @Autowired
    private EnvVariableService service;

    @Override
    public BaseEntityService<EnvVariable> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<EnvVariableDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}