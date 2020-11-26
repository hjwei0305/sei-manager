package com.changhong.sei.deploy.dto;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 18:43
 */
public enum ApplicationType {
    /**
     * 应用(新建应用申请)
     */
    APPLICATION,
    /**
     * 模块(新建模块申请)
     */
    MODULE,
    /**
     * 应用版本(版本申请)
     */
    VERSION,
    /**
     * 应用发布(申请发布测试/生产环境)
     */
    PUBLISH,
    /**
     * 应用部署(实施项目申请应用部署)
     */
    DEPLOY;
}
