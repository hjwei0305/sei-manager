package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.manager.dto.FeatureDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 功能项API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 20:49
 */
@FeignClient(name = "sei-manager", path = "feature")
public interface FeatureApi extends BaseEntityApi<FeatureDto>, FindByPageApi<FeatureDto> {

    /**
     * 根据过滤条件获取功能项
     *
     * @param search 过滤条件
     * @return 功能项列表
     */
    @PostMapping(path = "findByFilters", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据过滤条件获取功能项", notes = "根据过滤条件获取功能项")
    ResultData<List<FeatureDto>> findByFilters(@RequestBody Search search);

    /**
     * 根据功能项id查询子功能项
     *
     * @param featureId 功能项的id
     * @return 功能项列表
     */
    @GetMapping(path = "findChildByFeatureId")
    @ApiOperation(notes = "根据功能项id查询子功能项", value = "根据功能项id查询子功能项")
    ResultData<List<FeatureDto>> findChildByFeatureId(@RequestParam("featureId") String featureId);
}
