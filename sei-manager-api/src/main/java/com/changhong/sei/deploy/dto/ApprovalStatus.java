package com.changhong.sei.deploy.dto;

/**
 * 实现功能： 审核状态
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-27 17:35
 */
public enum ApprovalStatus {
    /**
     * 初始
     */
    initial,
    /**
     * 审核中
     */
    processing,
    /**
     * 未通过
     */
    unpassed,
    /**
     * 已通过
     */
    passed
}
