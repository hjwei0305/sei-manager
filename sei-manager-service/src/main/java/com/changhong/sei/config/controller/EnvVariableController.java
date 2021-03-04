package com.changhong.sei.config.controller;

import com.changhong.sei.config.api.EnvVariableApi;
import com.changhong.sei.config.dto.EnvVariableDto;
import com.changhong.sei.config.dto.EnvVariableValueDto;
import com.changhong.sei.config.entity.EnvVariable;
import com.changhong.sei.config.entity.EnvVariableValue;
import com.changhong.sei.config.service.EnvVariableService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * 获取所有环境变量key列表
     *
     * @return key列表
     */
    @Override
    public ResultData<List<EnvVariableDto>> getAllKey() {
        List<EnvVariableDto> dtoList;
        List<EnvVariable> list = service.findAllKey();
        if (CollectionUtils.isNotEmpty(list)) {
            dtoList = list.stream().map(v -> dtoModelMapper.map(v, EnvVariableDto.class)).collect(Collectors.toList());
        } else {
            dtoList = new ArrayList<>();
        }
        return ResultData.success(dtoList);
    }

    /**
     * 通过key获取各个环境变量清单
     *
     * @param code 环境变量key
     * @return 环境变量清单
     */
    @Override
    public ResultData<List<EnvVariableValueDto>> getVariableValues(String code) {
        List<EnvVariableValueDto> valueDtos;
        ResultData<List<EnvVariableValue>> resultData = service.findEnvVariableValues(code);
        if (resultData.successful()) {
            List<EnvVariableValue> variableValues = resultData.getData();
            if (CollectionUtils.isNotEmpty(variableValues)) {
                valueDtos = variableValues.stream().map(v -> dtoModelMapper.map(v, EnvVariableValueDto.class)).collect(Collectors.toList());
            } else {
                valueDtos = new ArrayList<>();
            }
            return ResultData.success(valueDtos);
        }
        return ResultData.fail(resultData.getMessage());
    }

    /**
     * 保存环境变量
     *
     * @param dtos 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> saveVariableValue(List<EnvVariableValueDto> dtos) {
        if (CollectionUtils.isNotEmpty(dtos)) {
            List<EnvVariableValue> values = dtos.stream().map(d -> entityModelMapper.map(d, EnvVariableValue.class)).collect(Collectors.toList());
            return service.saveVariableValue(values);
        } else {
            return ResultData.fail("持久化的环境变量不能为空.");
        }
    }

    /**
     * 删除环境变量
     *
     * @param ids 环境变量Id
     * @return 操作结果
     */
    @Override
    public ResultData<Void> deleteVariableValue(List<String> ids) {
        OperateResult result = service.deleteVariableValue(ids);
        return result.successful() ? ResultData.success() : ResultData.fail(result.getMessage());
    }
}