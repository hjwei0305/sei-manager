package com.changhong.sei.monitor.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.monitor.dto.ElasticSearchRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-04-06 16:39
 */
public interface ElasticIndexMonitorApi {

    /**
     * 获取所有索引
     */
    @GetMapping(path = "getAllIndex")
    @ApiOperation(value = "获取所有索引", notes = "获取所有索引")
    ResultData<Set<String>> getAllIndex();

    /**
     * 删除index
     */
    @DeleteMapping(path = "deleteIndex/{idxName}")
    @ApiOperation(value = "删除索引", notes = "删除索引")
    ResultData<String> deleteIndex(@PathVariable("idxName") String idxName);

    /**
     * 查询
     *
     * @param search 查询参数
     * @return java.util.List<T>
     */
    @PostMapping(value = "findByPage", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "分页查询", notes = "分页查询")
    ResultData<List<HashMap<String, Object>>> findByPage(@RequestBody @Valid ElasticSearchRequest search);
}
