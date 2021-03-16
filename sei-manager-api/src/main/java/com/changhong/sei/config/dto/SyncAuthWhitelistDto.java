package com.changhong.sei.config.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-16 15:18
 */
public class SyncAuthWhitelistDto implements Serializable {
    private static final long serialVersionUID = 3188193446576624877L;

    @NotBlank
    private String appCode;
    @NotBlank
    private String envCode;
    @NotEmpty
    private List<String> targetEnvList;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public List<String> getTargetEnvList() {
        return targetEnvList;
    }

    public void setTargetEnvList(List<String> targetEnvList) {
        this.targetEnvList = targetEnvList;
    }
}
