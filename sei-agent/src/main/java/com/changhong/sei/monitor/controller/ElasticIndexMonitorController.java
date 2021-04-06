package com.changhong.sei.monitor.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.monitor.api.ElasticIndexMonitorApi;
import com.changhong.sei.monitor.dto.ElasticSearchRequest;
import com.changhong.sei.monitor.service.ElasticIndexMonitorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RefreshScope
@Api(value = "ElasticIndexMonitorApi", tags = "全文检索API服务")
@RequestMapping(path = "elasticSearch", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElasticIndexMonitorController implements ElasticIndexMonitorApi {

    @Autowired
    private ElasticIndexMonitorService service;

    /**
     * 获取所有索引
     */
    @Override
    public ResultData<Set<String>> getAllIndex() {
        return service.getAllIndex();
    }

    /**
     * 删除index
     */
    @Override
    public ResultData<String> deleteIndex(String idxName) {
        return service.deleteIndex(idxName);
    }

    /**
     * 查询
     *
     * @param search 查询参数
     * @return java.util.List<T>
     */
    @Override
    public ResultData<List<HashMap<String, Object>>> findByPage(@Valid ElasticSearchRequest search) {
        return service.search(search);
    }
}
