package com.changhong.sei.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-18 16:44
 */
@ApiModel(description = "忘记密码请求")
public class ForgetPasswordRequest implements Serializable {

    private static final long serialVersionUID = 3614613734469635633L;
    @NotBlank
    @ApiModelProperty(value = "请求id", required = true)
    private String reqId;
    @NotBlank
    @ApiModelProperty(value = "验证码", required = true)
    private String verifyCode;
    /**
     * 用户名
     */
    @NotBlank
    @ApiModelProperty(value = "账号或邮箱或手机号", required = true)
    private String usernameOrEmailOrPhone;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUsernameOrEmailOrPhone() {
        return usernameOrEmailOrPhone;
    }

    public void setUsernameOrEmailOrPhone(String usernameOrEmailOrPhone) {
        this.usernameOrEmailOrPhone = usernameOrEmailOrPhone;
    }
}
