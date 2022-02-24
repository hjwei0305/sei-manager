package com.changhong.sei.ge.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.api.RuntimeEnvApi;
import com.changhong.sei.ge.dto.RuntimeEnvDto;
import com.changhong.sei.ge.entity.RuntimeEnv;
import com.changhong.sei.ge.service.RuntimeEnvService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@RestController
@Api(value = "RuntimeEnvApi", tags = "运行环境服务")
@RequestMapping(path = "env", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuntimeEnvController extends BaseEntityController<RuntimeEnv, RuntimeEnvDto> implements RuntimeEnvApi {
    /**
     * 服务器节点服务对象
     */
    @Autowired
    private RuntimeEnvService service;

    @Override
    public BaseEntityService<RuntimeEnv> getService() {
        return service;
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<RuntimeEnvDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<RuntimeEnvDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}