package com.changhong.sei.manager.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.api.FeatureApi;
import com.changhong.sei.manager.dto.FeatureDto;
import com.changhong.sei.manager.entity.Feature;
import com.changhong.sei.manager.service.FeatureService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 功能项API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 21:18
 */
@RestController
@Api(value = "FeatureApi", tags = "功能项API服务")
@RequestMapping(path = "feature", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FeatureController extends BaseEntityController<Feature, FeatureDto> implements FeatureApi {
    @Autowired
    private FeatureService service;

    @Override
    public BaseEntityService<Feature> getService() {
        return service;
    }

    /**
     * 根据过滤条件获取功能项
     *
     * @param search 过滤条件
     * @return 功能项列表
     */
    @Override
    public ResultData<List<FeatureDto>> findByFilters(Search search) {
        List<Feature> features = service.findByFilters(search);
        List<FeatureDto> dtos = features.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据功能项id查询子功能项
     *
     * @param featureId 功能项的id
     * @return 功能项列表
     */
    @Override
    public ResultData<List<FeatureDto>> findChildByFeatureId(String featureId) {
        List<Feature> features = service.findChildByFeatureId(featureId);
        List<FeatureDto> dtos = features.stream()
                .map(this::convertToDto)
                .sorted(Comparator.comparing(FeatureDto::getRank))
                .collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<FeatureDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}
