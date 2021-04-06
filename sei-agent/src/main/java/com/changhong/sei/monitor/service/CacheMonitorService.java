package com.changhong.sei.monitor.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-27 12:45
 */
@Service
public class CacheMonitorService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取key.当前缀为空是,获取所有key
     *
     * @param prefix key前缀
     * @return 返回key
     */
    public Set<String> getKey(String prefix) {
        Set<String> keys;
        if (StringUtils.isNotBlank(prefix)) {
            keys = redisTemplate.keys(prefix.concat("*"));
        } else {
            keys = redisTemplate.keys("*");
        }
        return keys;
    }

    /**
     * 获取缓存值
     *
     * @param key key
     * @return value
     */
    public String getValue(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        return Objects.isNull(obj) ? "" : obj.toString();
    }

    /**
     * 获取缓存过期时间(秒)
     *
     * @param key key
     * @return value
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 删除缓存
     *
     * @param key key
     * @return value
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
