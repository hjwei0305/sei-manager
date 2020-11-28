package com.changhong.sei.deploy.dto;

import java.io.Serializable;

/**
 * 实现功能：提交申请单
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-27 18:05
 */
public class ApprovalSubmitRequest implements Serializable {
    private static final long serialVersionUID = -5619138703809373270L;

    /**
     * 申请单id
     */
    private String requisitionId;
    /**
     * 处理日志
     */
    private String handleLog;

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public String getHandleLog() {
        return handleLog;
    }

    public void setHandleLog(String handleLog) {
        this.handleLog = handleLog;
    }
}
