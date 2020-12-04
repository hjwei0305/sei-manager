package com.changhong.sei.deploy.dto;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能：Jenkins任务构建状态
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-04 09:17
 */
public enum BuildStatus {
    @Remark("失败")
    FAILURE,

    @Remark("不稳定")
    UNSTABLE,

    @Remark("重建中")
    REBUILDING,

    @Remark("构建中")
    BUILDING,

    /**
     * This means a job was already running and has been aborted.
     */
    @Remark("已终止")
    ABORTED,

    /**
     *
     */
    @Remark("成功")
    SUCCESS,

    /**
     * ?
     */
    @Remark("未知")
    UNKNOWN,

    /**
     * This is returned if a job has never been built.
     */
    @Remark("未构建")
    NOT_BUILT,

    /**
     * This will be the result of a job in cases where it has been cancelled
     * during the time in the queue.
     */
    @Remark("被取消")
    CANCELLED
}
