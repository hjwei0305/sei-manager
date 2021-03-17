package com.changhong.sei.cicd.dto;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能：流程操作
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-27 17:33
 */
public enum OperationType {
    /**
     * 提交
     */
    @Remark("提交")
    SUBMIT,
    /**
     * 通过
     */
    @Remark("通过")
    PASSED,
    /**
     * 驳回
     */
    @Remark("驳回")
    REJECT,
    /**
     * 取消
     */
    @Remark("取消")
    CANCEL
}
