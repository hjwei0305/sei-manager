package com.changhong.sei.deploy.dto;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能：申请单类型
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 18:43
 */
public enum ApplyType {
    /**
     * 应用(新建应用申请)
     */
    @Remark("应用申请")
    APPLICATION,
    /**
     * 模块(新建模块申请)
     */
    @Remark("模块申请")
    MODULE,
    /**
     * 应用版本(版本申请)
     */
    @Remark("发版申请")
    PUBLISH,
    /**
     * 应用部署(申请发布测试/生产环境)
     */
    @Remark("部署申请")
    DEPLOY,
    /**
     * 应用项目(实施项目申请应用部署)
     */
    @Remark("项目申请")
    PROJECT;
}
