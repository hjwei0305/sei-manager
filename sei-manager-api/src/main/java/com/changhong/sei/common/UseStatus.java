package com.changhong.sei.common;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-02 14:31
 */
public enum UseStatus {
    /**
     * NONE
     */
    @Remark("NONE")
    NONE,
    /**
     * 启用
     */
    @Remark("启用")
    ENABLE,
    /**
     * 禁用
     */
    @Remark("禁用")
    DISABLE
}
