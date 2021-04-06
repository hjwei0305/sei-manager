package com.changhong.sei.monitor.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.monitor.api.CacheMonitorApi;
import com.changhong.sei.monitor.service.CacheMonitorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-04-06 16:25
 */
@RestController
@RefreshScope
@Api(value = "CacheMonitorApi", tags = "缓存API服务")
@RequestMapping(path = "cacheMonitor", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheMonitorController implements CacheMonitorApi {

    @Autowired
    private CacheMonitorService service;

    /**
     * 获取key.当前缀为空是,获取所有key
     *
     * @param prefix key前缀
     * @return 返回key
     */
    @Override
    public ResultData<Set<String>> getKey(String prefix) {
        return ResultData.success(service.getKey(prefix));
    }

    /**
     * 获取缓存值和超时时间(秒)
     *
     * @param key key
     * @return value
     */
    @Override
    public ResultData<Map<String, Object>> getDetail(String key) {
        Map<String, Object> data = new HashMap<>();
        Long expire = service.getExpire(key);
        data.put("expire", expire);
        data.put("value", service.getValue(key));
        return ResultData.success(data);
    }

    /**
     * 删除缓存
     *
     * @param key key
     * @return value
     */
    @Override
    public ResultData<Boolean> delete(String key) {
        return ResultData.success(service.delete(key));
    }
}
