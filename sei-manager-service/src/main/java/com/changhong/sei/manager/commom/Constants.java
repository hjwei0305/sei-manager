package com.changhong.sei.manager.commom;

/**
 * 实现功能：常量定义
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-11 16:42
 */
public interface Constants {
    /**
     * 空值
     */
    String NULL_EMPTY = "NULL_EMPTY";
    /**
     * JWT 在 Redis 中保存的key前缀
     */
    String REDIS_JWT_KEY_PREFIX = "sei:manager:jwt:";

    /**
     * 认证码缓存key
     */
    String REDIS_VERIFY_CODE_KEY = "sei:auth:verify_code:";
    /**
     * 注册缓存key
     */
    String REDIS_REGISTERED_KEY = "sei:auth:registered:";

}
