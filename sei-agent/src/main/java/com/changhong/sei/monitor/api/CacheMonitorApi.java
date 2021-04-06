package com.changhong.sei.monitor.api;

import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-04-06 16:29
 */
public interface CacheMonitorApi {

    /**
     * 获取key.当前缀为空是,获取所有key
     *
     * @param prefix key前缀
     * @return 返回key
     */
    @GetMapping(path = "getKey")
    @ApiOperation(value = "获取key", notes = "获取key.当前缀为空是,获取所有key")
    ResultData<Set<String>> getKey(@RequestParam(name = "prefix", required = false) String prefix);

    /**
     * 获取缓存值和超时时间(秒)
     *
     * @param key key
     * @return value
     */
    @GetMapping(path = "getDetail")
    @ApiOperation(value = "获取缓存值和超时时间(秒)", notes = "获取缓存值和超时时间(秒)")
    ResultData<Map<String, Object>> getDetail(@RequestParam(name = "key") String key);

    /**
     * 删除缓存
     *
     * @param key key
     * @return value
     */
    @DeleteMapping("delete/{key}")
    ResultData<Boolean> delete(@PathVariable("key") String key);
}
